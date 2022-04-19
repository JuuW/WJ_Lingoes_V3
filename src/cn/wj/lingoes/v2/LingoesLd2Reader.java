package cn.wj.lingoes.v2;
/*  Copyright (c) 2010 Xiaoyun Zhu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy  
 *  of this software and associated documentation files (the "Software"), to deal  
 *  in the Software without restriction, including without limitation the rights  
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
 *  copies of the Software, and to permit persons to whom the Software is  
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in  
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN  
 *  THE SOFTWARE.  
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import cn.wj.lingoes.v2.entity.Dict;

public class LingoesLd2Reader {

	private Dict dict;
	private Statement stmt;

	private static final SensitiveStringDecoder[] AVAIL_ENCODINGS = {
			new SensitiveStringDecoder(Charset.forName("UTF-8")),
			new SensitiveStringDecoder(Charset.forName("UTF-16LE")),
			new SensitiveStringDecoder(Charset.forName("UTF-16BE")),
			new SensitiveStringDecoder(Charset.forName("EUC-JP")) };

	public LingoesLd2Reader(Dict dict, Statement stmt) {
		this.stmt = stmt;
		this.dict = dict;
	}

	public static void main(final String[] args) throws IOException {

		final String ld2File;
		ld2File = "/Users/I329722/Desktop/柯林斯高阶英语学习字典第五版.ld2";

		Dict d = new Dict("词典");
		d.setFilePath(ld2File);
	}

	public void extractLd2ToMap() throws IOException {

		String ld2File = dict.getFilePath();

		final ByteBuffer dataRawBytes;
		try (RandomAccessFile file = new RandomAccessFile(ld2File, "r");
				final FileChannel fChannel = file.getChannel();) {
			dataRawBytes = ByteBuffer.allocate((int) fChannel.size());
			fChannel.read(dataRawBytes);
		}
		dataRawBytes.order(ByteOrder.LITTLE_ENDIAN);
		dataRawBytes.rewind();

		System.out.println("文件：" + ld2File);

		final int offsetData = dataRawBytes.getInt(0x5C) + 0x60;
		if (dataRawBytes.limit() > offsetData) {
			final int type = dataRawBytes.getInt(offsetData);
			final int offsetWithInfo = dataRawBytes.getInt(offsetData + 4) + offsetData + 12;
			if (type == 3) {
				readDictionary(ld2File, dataRawBytes, offsetData);
			} else if (dataRawBytes.limit() > (offsetWithInfo - 0x1C)) {
				readDictionary(ld2File, dataRawBytes, offsetWithInfo);
			} else {
				System.err.println("文件不包含字典数据。网上字典？");
			}
		} else {
			System.err.println("文件不包含字典数据。网上字典？");
		}
	}

	private static final long decompress(final String inflatedFile, final ByteBuffer data, final int offset,
			final int length, final boolean append) throws IOException {
		final Inflater inflator = new Inflater();
		try (final InflaterInputStream in = new InflaterInputStream(
				new ByteArrayInputStream(data.array(), offset, length), inflator, 1024 * 8);
				final FileOutputStream out = new FileOutputStream(inflatedFile, append);) {
			LingoesLd2Reader.writeInputStream(in, out);
		}
		final long bytesRead = inflator.getBytesRead();
		inflator.end();
		return bytesRead;

	}

	private static final SensitiveStringDecoder[] detectEncodings(final ByteBuffer inflatedBytes, final int offsetWords,
			final int offsetXml, final int defTotal, final int dataLen, final int[] idxData, final String[] defData) {
		final int test = Math.min(defTotal, 10);
		for (int j = 0; j < LingoesLd2Reader.AVAIL_ENCODINGS.length; j++) {
			for (int k = 0; k < LingoesLd2Reader.AVAIL_ENCODINGS.length; k++) {
				try {
					for (int i = 0; i < test; i++) {
						LingoesLd2Reader.readDefinitionData(inflatedBytes, offsetWords, offsetXml, dataLen,
								LingoesLd2Reader.AVAIL_ENCODINGS[j], LingoesLd2Reader.AVAIL_ENCODINGS[k], idxData,
								defData, i);
					}
					return new SensitiveStringDecoder[] { LingoesLd2Reader.AVAIL_ENCODINGS[j],
							LingoesLd2Reader.AVAIL_ENCODINGS[k] };
				} catch (final Throwable e) {
				}
			}
		}
		System.err.println("自动识别编码失败！选择UTF-16LE继续。");
		return new SensitiveStringDecoder[] { LingoesLd2Reader.AVAIL_ENCODINGS[1],
				LingoesLd2Reader.AVAIL_ENCODINGS[1] };
	}

	private final void extract(final String inflatedFile, final String indexFile, final String extractedWordsFile,
			final String extractedXmlFile, final String extractedOutputFile, final int[] idxArray, final int offsetDefs,
			final int offsetXml) throws IOException, FileNotFoundException, UnsupportedEncodingException {

		int counter = 0;
		try (RandomAccessFile file = new RandomAccessFile(inflatedFile, "r");
				final FileChannel fChannel = file.getChannel();) {
			final ByteBuffer dataRawBytes = ByteBuffer.allocate((int) fChannel.size());
			fChannel.read(dataRawBytes);
			fChannel.close();
			dataRawBytes.order(ByteOrder.LITTLE_ENDIAN);
			dataRawBytes.rewind();

			final int dataLen = 10;
			final int defTotal = (offsetDefs / dataLen) - 1;

			final String[] words = new String[defTotal];
			final int[] idxData = new int[6];
			final String[] defData = new String[2];

			final SensitiveStringDecoder[] encodings = LingoesLd2Reader.detectEncodings(dataRawBytes, offsetDefs,
					offsetXml, defTotal, dataLen, idxData, defData);

			dataRawBytes.position(8);

			for (int i = 0; i < defTotal; i++) {
				LingoesLd2Reader.readDefinitionData(dataRawBytes, offsetDefs, offsetXml, dataLen, encodings[0],
						encodings[1], idxData, defData, i);

				words[i] = defData[0];
				counter++;
				dict.getDictindex().add(defData[0].toLowerCase());

				String word = defData[0].toLowerCase().replace("'", "''");
				String value = defData[1].toString().replace("'", "''");
				try {

					String sql = "INSERT INTO dict (dict_ID,WORD,VALUE) VALUES ('" + dict.getId() + "', '" + word
							+ "', '" + value + "' );";

					stmt.execute(sql);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(dict.getId() + "," + word + "," + value);
					break;
				}
			}

		}
		System.out.println("成功读出" + counter + "组数据。");

	}

	private static final void getIdxData(final ByteBuffer dataRawBytes, final int position, final int[] wordIdxData) {
		dataRawBytes.position(position);
		wordIdxData[0] = dataRawBytes.getInt();
		wordIdxData[1] = dataRawBytes.getInt();
		wordIdxData[2] = dataRawBytes.get() & 0xff;
		wordIdxData[3] = dataRawBytes.get() & 0xff;
		wordIdxData[4] = dataRawBytes.getInt();
		wordIdxData[5] = dataRawBytes.getInt();
	}

	private static final void inflate(final ByteBuffer dataRawBytes, final List<Integer> deflateStreams,
			final String inflatedFile) {
		System.out.println("解压缩'" + deflateStreams.size() + "'个数据流至'" + inflatedFile + "'。。。");
		final int startOffset = dataRawBytes.position();
		int offset = -1;
		int lastOffset = startOffset;
		boolean append = false;
		try {
			for (final Integer offsetRelative : deflateStreams) {
				offset = startOffset + offsetRelative.intValue();
				LingoesLd2Reader.decompress(inflatedFile, dataRawBytes, lastOffset, offset - lastOffset, append);
				append = true;
				lastOffset = offset;
			}
		} catch (final Throwable e) {
			System.err.println("解压缩失败: 0x" + Integer.toHexString(offset) + ": " + e.toString());
		}
	}

	private static final void readDefinitionData(final ByteBuffer inflatedBytes, final int offsetWords,
			final int offsetXml, final int dataLen, final SensitiveStringDecoder wordStringDecoder,
			final SensitiveStringDecoder xmlStringDecoder, final int[] idxData, final String[] defData, final int i) {
		LingoesLd2Reader.getIdxData(inflatedBytes, dataLen * i, idxData);
		int lastWordPos = idxData[0];
		int lastXmlPos = idxData[1];
		// final int flags = idxData[2];
		int refs = idxData[3];
		final int currentWordOffset = idxData[4];
		int currenXmlOffset = idxData[5];

		String xml = LingoesLd2Reader.strip(new String(
				xmlStringDecoder.decode(inflatedBytes.array(), offsetXml + lastXmlPos, currenXmlOffset - lastXmlPos)));
		while (refs-- > 0) {
			final int ref = inflatedBytes.getInt(offsetWords + lastWordPos);
			LingoesLd2Reader.getIdxData(inflatedBytes, dataLen * ref, idxData);
			lastXmlPos = idxData[1];
			currenXmlOffset = idxData[5];
			if (xml.isEmpty()) {
				xml = LingoesLd2Reader.strip(new String(xmlStringDecoder.decode(inflatedBytes.array(),
						offsetXml + lastXmlPos, currenXmlOffset - lastXmlPos)));
			} else {

				xml = LingoesLd2Reader.strip(new String(xmlStringDecoder.decode(inflatedBytes.array(),
						offsetXml + lastXmlPos, currenXmlOffset - lastXmlPos))) + ", " + xml;
			}
			lastWordPos += 4;
		}
		defData[1] = xml;

		final String word = new String(wordStringDecoder.decode(inflatedBytes.array(), offsetWords + lastWordPos,
				currentWordOffset - lastWordPos));
		defData[0] = word;
	}

	private final void readDictionary(final String ld2File, final ByteBuffer dataRawBytes, final int offsetWithIndex)
			throws IOException, FileNotFoundException, UnsupportedEncodingException {
		final int limit = dataRawBytes.getInt(offsetWithIndex + 4) + offsetWithIndex + 8;
		final int offsetIndex = offsetWithIndex + 0x1C;
		final int offsetCompressedDataHeader = dataRawBytes.getInt(offsetWithIndex + 8) + offsetIndex;
		final int inflatedWordsIndexLength = dataRawBytes.getInt(offsetWithIndex + 12);
		final int inflatedWordsLength = dataRawBytes.getInt(offsetWithIndex + 16);
		final int inflatedXmlLength = dataRawBytes.getInt(offsetWithIndex + 20);
		final int definitions = (offsetCompressedDataHeader - offsetIndex) / 4;
		final List<Integer> deflateStreams = new ArrayList<>();
		dataRawBytes.position(offsetCompressedDataHeader + 8);
		int offset = dataRawBytes.getInt();
		while ((offset + dataRawBytes.position()) < limit) {
			offset = dataRawBytes.getInt();
			deflateStreams.add(Integer.valueOf(offset));
		}
		final int offsetCompressedData = dataRawBytes.position();
		System.out.println("索引词组数目：" + definitions);
		final String inflatedFile = ld2File + ".inflated";

		if (!new File(inflatedFile).exists()) {
			LingoesLd2Reader.inflate(dataRawBytes, deflateStreams, inflatedFile);
		}

		if (new File(inflatedFile).isFile()) {
			final String indexFile = ld2File + ".idx";
			final String extractedFile = ld2File + ".words";
			final String extractedXmlFile = ld2File + ".xml";
			final String extractedOutputFile = ld2File + ".output";

			dataRawBytes.position(offsetIndex);
			final int[] idxArray = new int[definitions];
			for (int i = 0; i < definitions; i++) {
				idxArray[i] = dataRawBytes.getInt();
			}
			extract(inflatedFile, indexFile, extractedFile, extractedXmlFile, extractedOutputFile, idxArray,
					inflatedWordsIndexLength, inflatedWordsIndexLength + inflatedWordsLength);
		}
	}

	private static String strip(String xml) {
		int open = 0;
		int end = 0;
		String result = "";
		if ((open = xml.indexOf("<![CDATA[")) != -1) {
			result = xml.replace("<![CDATA[", "").replace("]]>", "").replace("<Ë M=", "<strong>-></strong><E M=");
		} else {

			result = xml.replace("<v>", "<span style='color:#0A0;font-style:italic'>").replace("</v>", "</span>")
					.replace("<h>", "<span style='color:#0A0'>").replace("</h>", "</span>").replace("<g>", "<b>")
					.replace("</g>", "</b>").replace("<n />", "<br/>").replace("<Ë M=", "<strong>-></strong><E M=")
					.replace("<I>", "").replace("</I>", "").replace("<Ô", "<o").replace("Ô>", "o>");
		}
		return result.replace("'", "''");
	}

	private static final void writeInputStream(final InputStream in, final OutputStream out) throws IOException {
		final byte[] buffer = new byte[1024 * 8];
		int len;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
	}

	private static class SensitiveStringDecoder {
		public final String name;
		private final CharsetDecoder cd;

		SensitiveStringDecoder(final Charset cs) {
			this.cd = cs.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
					.onUnmappableCharacter(CodingErrorAction.REPORT);
			this.name = cs.name();
		}

		char[] decode(final byte[] ba, final int off, final int len) {
			final int en = (int) (len * (double) this.cd.maxCharsPerByte());
			final char[] ca = new char[en];
			if (len == 0) {
				return ca;
			}
			this.cd.reset();
			final ByteBuffer bb = ByteBuffer.wrap(ba, off, len);
			final CharBuffer cb = CharBuffer.wrap(ca);
			try {
				CoderResult cr = this.cd.decode(bb, cb, true);
				if (!cr.isUnderflow()) {
					cr.throwException();
				}
				cr = this.cd.flush(cb);
				if (!cr.isUnderflow()) {
					cr.throwException();
				}
			} catch (final CharacterCodingException x) {
				throw new Error(x);
			}
			return SensitiveStringDecoder.safeTrim(ca, cb.position());
		}

		private static char[] safeTrim(final char[] ca, final int len) {
			if (len == ca.length) {
				return ca;
			} else {
				return Arrays.copyOf(ca, len);
			}
		}
	}
}
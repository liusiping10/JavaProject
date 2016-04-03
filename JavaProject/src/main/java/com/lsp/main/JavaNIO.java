package com.lsp.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * nio 网络IO
 * @author liusp
 *
 */
public class JavaNIO {
	public static void main(String[] args) {
		// bufferNIO();
		// channelNIO_WriteData();
		//channelNIO_ReadData();
	
		channelMap();
	}
	
	private static void channelMap() {
		File fileInput = new File("d://README.md");

		FileInputStream input = null;
		try {
			input = new FileInputStream(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		FileChannel fileChannlInput = null;
		fileChannlInput = input.getChannel();
		
		MappedByteBuffer mappedByteBuffer=null;
		
		try {
			mappedByteBuffer=fileChannlInput.map(MapMode.READ_ONLY, 0, fileInput.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		byte[] data=new byte[(int)fileInput.length()];
		int index=0;
		while(mappedByteBuffer.hasRemaining()){
			data[index++]=mappedByteBuffer.get();
		}
		
		System.out.println(new String(data));
		
		try {
			fileChannlInput.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void channelNIO_ReadData() {
		File fileInput = new File("d://nio.txt");
		File fileOutput = new File("d://nioout.txt");

		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(fileInput);
			output = new FileOutputStream(fileOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FileChannel fileChannlInput = null;
		FileChannel fileChannlOutput = null;

		fileChannlInput = input.getChannel();
		fileChannlOutput = output.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int tmp = 0;

		try {
			while ((tmp = fileChannlInput.read(buffer)) != -1) {
				buffer.flip();
				fileChannlOutput.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			input.close();
			output.close();
			fileChannlInput.close();
			fileChannlOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void channelNIO_WriteData() {
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;
		String[] data = { "Scala", "Spark", "Java", "Hadoop" };

		File file = new File("d://nio.txt");
		try {
			fileOutputStream = new FileOutputStream(file);
			fileChannel = fileOutputStream.getChannel();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		for (String item : data) {
			buffer.put(item.getBytes());
		}

		buffer.flip();

		try {
			fileChannel.write(buffer);
			fileChannel.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void bufferNIO() {
		// ByteBuffer
		// CharBuffer
		// ShortBuffer
		// FloatBuffer
		// DoubleBuffer

		// ����ֱ�ӻ�����
		ByteBuffer.allocateDirect(10);

		IntBuffer buffer = IntBuffer.allocate(100);

		System.out.println("position=" + buffer.position() + ",limit="
				+ buffer.limit() + ",capacity=" + buffer.capacity());

		int[] data = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		buffer.put(100);
		buffer.put(data);

		System.out.println("position=" + buffer.position() + ",limit="
				+ buffer.limit() + ",capacity=" + buffer.capacity());

		buffer.flip();// ���軺����
		// buffer.reset();
		// buffer.limit(10);
		// buffer.slice();

		System.out.println("position=" + buffer.position() + ",limit="
				+ buffer.limit() + ",capacity=" + buffer.capacity());

		while (buffer.hasRemaining()) {
			System.out.println(buffer.get());
		}

		buffer.position(2);
		buffer.limit(4);

		IntBuffer sliced = buffer.slice();

		for (int i = 0; i < sliced.capacity(); ++i) {
			int item = sliced.get(i);
			sliced.put(i, item - 100);
		}
		System.out.println("-----------");

		buffer.flip();
		while (buffer.hasRemaining()) {
			System.out.println(buffer.get());
		}
		System.out.println("-----------");

		while (sliced.hasRemaining()) {
			System.out.println(sliced.get());
		}
	}
}

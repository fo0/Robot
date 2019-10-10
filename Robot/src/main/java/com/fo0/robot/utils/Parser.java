package com.fo0.robot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Parser {

	public static <T> void write(T obj, File destFile) {
		if (obj == null || destFile == null)
			return;

		if (!destFile.exists())
			try {
				destFile.createNewFile();
			} catch (IOException e1) {
			}

		try (Writer writer = new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(obj, writer);
			writer.close();
		} catch (IOException e) {
			Logger.error("failed to read json Object " + obj.getClass() + " " + e);
		}
	}

	public static <T> List<T> parseList(File file, Class<T> _Class) {
		return parse(file, TypeToken.getParameterized(ArrayList.class, _Class).getType());
	}

	public static <T> T parse(File file, Type type) {
		if (file == null || !file.exists()) {
			return null;
		}

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(file);

			T obj = new Gson().fromJson(new InputStreamReader(fis, "UTF-8"), type);

			if (obj == null) {
				return null;
			}

			return obj;
		} catch (Exception e) {
			Logger.error("failed to parse File: " + file + " " + e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				Logger.error("failed to close FileInputStream " + e);
			}
		}

		return null;
	}

	public static <T> T read(File file, Class<T> clazz) {
		if (file == null || !file.exists())
			return null;

		try {
			return new GsonBuilder().setPrettyPrinting().create().fromJson(
					new InputStreamReader(new FileInputStream(file), "UTF-8"),
					TypeToken.getParameterized(clazz).getType());
		} catch (Exception e) {
			Logger.error("failed parse File: " + file + " " + e);
		}

		return null;
	}

}

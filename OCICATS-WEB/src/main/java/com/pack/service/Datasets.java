package com.pack.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import twitter4j.Status;

public class Datasets {

	public String tweetsFilter(List<Status> tweets, String defaultPath) throws IOException {
		Status temptweet = null;
		String[] text = null;
		StringBuffer textf = new StringBuffer();
		String textword = null;
		String test1 = null;
		String test2 = null;
		for (int i = 0; i < tweets.size(); i++) {
			temptweet = tweets.get(i);
			text = temptweet.getText().split(" ");
			for (int j = 0; j < text.length; j++) {
				textword = text[j];
				if (textword.length() >= 2) {
					try {
						test1 = textword.substring(0, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (textword.length() > 4) {
						test2 = textword.substring(0, 4);
					}

					if (!"RT".equals(textword) && !test1.equals("@")) {
						if (textword.length() > 4) {
							if (!test2.equals("http")) {
								textf.append(textword).append(" ");
							}

						} else {
							textf.append(textword).append(" ");
						}
					}
				}

			}

		}

		String s = saveTxt(textf.toString(), defaultPath);

		return s;
	}

	private String saveTxt(String text, String defaultPath) throws IOException {
		Date time = new Date();
		String path =  defaultPath + "resources/files/" + time.getTime() + ".txt";

		try {
			System.out.println(path);
			File f = new File(path);
			f.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(text);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return path;
	}
}

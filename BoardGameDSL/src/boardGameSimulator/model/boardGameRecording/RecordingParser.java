package boardGameSimulator.model.boardGameRecording;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import boardGameSimulator.model.util.StringUtil;

public class RecordingParser {

	public static GameRecording parseFile(FileReader fr) {
		GameRecording record = new GameRecording();
		BufferedReader br = new BufferedReader(fr);

		// TODO handle setup
		try {
			String line = br.readLine();
			String error;
			while (line != null) {
				error = parseLine(line, record);

				line = br.readLine();
				if(error != null){
					System.out.println(error);
					return null;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return record;
	}

	public static String parseLine(String line, GameRecording record) {
		if (line.startsWith("//")) {
			// skip reading this line
			return null;
		}
		String[] args = line.split(" ");
		switch (args[0]) {
		case ("R"):
			if (args.length < 2) {
				return "Invalid recording, Random line without int";
			} else if (!StringUtil.isInteger(args[1])) {
				return "Invalid recording, Random line argument could not be resolved to integer: ";
			} else {
				String value = "?";
				if (args.length > 2) {
					value = args[2];
				}
				record.writeRandom(Integer.parseInt(args[1]), value);
				return null;
			}

		case ("A"):
			if (args.length < 3) {
				return "Invalid recording, Action line without Action & Player Name";
			} else {
				String[] arguments = {};
				if(args.length > 3){
					arguments = Arrays.copyOfRange(args, 3, args.length);
				}
				

				record.writeAction(args[1], args[2], arguments);
				return null;
			}
		
		default:
			return "Invalid recording, unknown command!";

		}
	}

}

import java.io.*;
import java.util.*;

public class LyricExaminer {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Welcome to Lyrics Examiner!");
		System.out.println();
		Scanner console = new Scanner(System.in);
		Scanner inFile =  getFileScanner(console);

		Map<String, List<Integer>> map = createMap(inFile);
		System.out.println();
		displayWords(map);
		System.out.println("\nNumber of unique words: " + map.size());
		System.out.println("\nMost frequent word(s): " + mostFrequentWords(map));
		System.out.println();
		displayLyrics(map);

	}

	/**
	 * Requires the user to enter a filename until the filename is valid
	 * 
	 * @param console - facilitates keyboard input
	 * @return Scanner connected to the input file
	 * @throws FileNotFoundException if the file is not found
	 */
	public static Scanner getFileScanner(Scanner console) throws FileNotFoundException {
		System.out.print("Enter the filename with the lyrics: ");
		String filename = console.nextLine();
		File inputFile = new File(filename);
		while (!inputFile.canRead()) {
			System.out.println("Error. File can not be found or can not be read.");
			System.out.print("Enter the filename with the lyrics: ");
			filename = console.nextLine();
			inputFile = new File(filename);
		}
		return new Scanner(inputFile);
	}
	
	/**
	 * creates the map from each unique word in the file to a list of the numeric
	 * position the the word in the file (ie. 3 if it is the 3rd word in the file).
	 * If the word is the last word on a line in the file, the position will be
	 * a negative number (ie -3 if it the 3rd word in the file and the end of a line)
	 * @param inFile - the Scanner connected to the song lyric file
	 * @return a map from each word to its list of positions
	 */
	public static Map<String, List<Integer>> createMap(Scanner inFile) {
		Map<String, List<Integer>> map = new TreeMap<String, List<Integer>>();
		int pos = 1;
		while (inFile.hasNextLine()) {
			String line = inFile.nextLine();
			if (line.length() != 0) {      // so that blank lines are skipped
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					words[i] = words[i].toUpperCase();
					if (i == words.length-1) {
						pos = pos*(-1);
					}
					if (map.containsKey(words[i])) {
						map.get(words[i]).add(pos);
					}
					else {
						ArrayList<Integer> posList = new ArrayList<Integer>();
						posList.add(pos);
						map.put(words[i], posList);
					}
					if (i == words.length-1) {
						pos = pos*(-1);
					}
					pos++;
				}
			}
		}
		return map;
	}

	/**
	 * prints each word in the song and its positions
	 * @param map - the map from each word in the song to its list of positions
	 */
	public static void displayWords(Map<String, List<Integer>> map) {
		System.out.printf("%-15s%s\n", "Word", "Word Positions");
		for (String item : map.keySet()) {
			String posList = map.get(item).toString();
			System.out.printf("%-15s%s\n", item, posList.substring(1, posList.length()-1));
		}
	}

	/**
	 * reassembles the song lyrics from the map and prints it
	 * @param map - the map from each word in the song to its list of positions
	 */
	public static void displayLyrics(Map<String, List<Integer>> map) {
		int size = 0;
		for (String word : map.keySet()) {
			size += map.get(word).size();
		}
		String[] lyrics = new String[size+1];
		lyrics[0] = null;
		for (String word : map.keySet()) {
			for (int pos : map.get(word)) {
				if (pos < 0) {
					lyrics[-pos] = word + "\n";
				}
				else {
					lyrics[pos] = word + " ";
				}
			}
		}
		for (int i = 1; i <= size; i++) {
			System.out.print(lyrics[i]);
		}
	}

	/**
	 * creates an alphabetical set with the most frequent word, or words if there is a tie,
	 * in the file
	 * @param map - the map from each word in the song to its list of positions
	 * @return the set of most frequent words in alphabetical order
	 */
	public static Set<String> mostFrequentWords(Map<String, List<Integer>> map) {
		Set<String> mostFrequentWords = new TreeSet<String>();
		int max = 0;
		for (String word : map.keySet()) {
			if (map.get(word).size() < max) {
				continue;
			}
			else if (map.get(word).size() == max) {
				mostFrequentWords.add(word);
			}
			else {
				max = map.get(word).size();
				mostFrequentWords.clear();
				mostFrequentWords.add(word);
			}
		}
		return mostFrequentWords;
	}

}

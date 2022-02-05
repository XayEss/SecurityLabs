package security.Security.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Part4 {

	private String encoded = "EFFPQLEKVTVPCPYFLMVHQLUEWCNVWFYGHYTCETHQEKLPVMSAKSPVPAPVYWMVHQLUSPQLYWLASLFVWPQLMVHQLUPLRPSQLULQESPBLWPCSVRVWFLHLWFLWPUEWFYOTCMQYSLWOYWYETHQEKLPVMSAKSPVPAPVYWHEPPLUWSGYULEMQTLPPLUGUYOLWDTVSQETHQEKLPVPVSMTLEUPQEPCYAMEWWYTYWDLUULTCYWPQLSEOLSVOHTLUYAPVWLYGDALSSVWDPQLNLCKCLRQEASPVILSLEUMQBQVMQCYAHUYKEKTCASLFPYFLMVHQLUPQLHULIVYASHEUEDUEHQBVTTPQLVWFLRYGMYVWMVFLWMLSPVTTBYUNESESADDLSPVYWCYAMEWPUCPYFVIVFLPQLOLSSEDLVWHEUPSKCPQLWAOKLUYGMQEUEMPLUSVWENLCEWFEHHTCGULXALWMCEWETCSVSPYLEMQYGPQLOMEWCYAGVWFEBECPYASLQVDQLUYUFLUGULXALWMCSPEPVSPVMSBVPQPQVSPCHLYGMVHQLUPQLWLRPOEDVMETBYUFBVTTPENLPYPQLWLRPTEKLWZYCKVPTCSTESQPBYMEHVPETCMEHVPETZMEHVPETKTMEHVPETCMEHVPETT"
			+ "UMUPLYRXOYRCKTYYPDYZTOUYDZHYJYUNTOMYTOLTKAOHOKZCMKAVZDYBRORPTHQLSERUOERMKZGQJOIDJUDNDZATUVOTTLMQBOWNMERQTDTUFKZCMTAZMEOJJJOXMERKJHACMTAZATIZOEPPJKIJJNOCFEPLFBUNQHHPPKYYKQAZKTOTIKZNXPGQZQAZKTOTIZYNIUISZIAELMKSJOYUYYTHNEIEOESULOXLUEYGBEUGJLHAJTGGOEOSMJHNFJALFBOHOKAGPTIHKNMKTOUUUMUQUDATUEIRBKYUQTWKJKZNLDRZBLTJJJIDJYSULJARKHKUKBISBLTOJRATIOITHYULFBITOVHRZIAXFDRNIORLZEYUUJGEBEYLNMYCZDITKUXSJEJCFEUGJJOTQEZNORPNUDPNQIAYPEDYPDYTJAIGJYUZBLTJJYYNTMSEJYFNKHOTJARNLHHRXDUPZIALZEDUYAOSBBITKKYLXKZNQEYKKZTOKHWCOLKURTXSKKAGZEPLSYHTMKRKJIIQZDTNHDYXMEIRMROGJYUMHMDNZIOTQEKURTXSKKAGZEPLSYHTMKRKJIIQZDTNROAUYLOTIMDQJYQXZDPUMYMYPYRQNYFNUYUJJEBEOMDNIYUOHYYYJHAOQDRKKZRRJEPCFNRKJUHSJOIRQYDZBKZURKDNNEOYBTKYPEJCMKOAJORKTKJLFIOQHYPNBTAVZEUOBTKKBOWSBKOSKZUOZIHQSLIJJMSURHYZJJZUKOAYKNIYKKZNHMITBTRKBOPNUYPNTTPOKKZNKKZNLKZCFNYTKKQNUYGQJKZNXYDNJYYMEZRJJJOXMERKJVOSJIOSIQAGTZYNZIOYSMOHQDTHMEDWJKIULNOTBCALFBJNTOGSJKZNEEYYKUIXLEUNLNHNMYUOMWHHOOQNUYGQJKZLZJZLOLATSEHQKTAYPYRZJYDNQDTHBTKYKYFGJRRUFEWNTHAXFAHHODUPZMXUMKXUFEOTIMUNQIHGPAACFKATIKIZBTOTIKZNKKZNLORUKMLLFBUUQKZNLEOHIEOHEDRHXOTLMIRKLEAHUYXCZYTGUYXCZYTIUYXCZYTCVJOEBKOHE";

	private Map<Character, Double> letterFrequency;
	private String[] letterSequences = new String[] { "etaoinshrdlucmfwypvbgkjqxz", "ETAONISRHDLUCMFWGYPBVKXJQZ", "teoainshrdlucmfwypvbgkjqxz" };

	public void substitutionalDecoder() {
		double[] frequencies = new double[letterSequences.length];
		letterFrequency = new LinkedHashMap<>();
		char[] letter = encoded.toCharArray();
		int length = encoded.length();
		for (char c : letter) {
			if (letterFrequency.containsKey(c)) {
				letterFrequency.put(c, letterFrequency.get(c) + 1d);
			} else {
				letterFrequency.put(c, 0d);
			}
		}
		for (Map.Entry<Character, Double> entry : letterFrequency.entrySet()) {
			entry.setValue(entry.getValue() / length);
		}
		letterFrequency = sortMapByFrequency(letterFrequency);
		for (Map.Entry<Character, Double> entry : letterFrequency.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		Map<Character, Character> substitution = new LinkedHashMap<>() ;
		List<Character> letters = new ArrayList<>(letterFrequency.keySet());
		for (int j = 0; j < letterSequences.length; j++) {
			for (int i = 0; i < letterFrequency.size(); i++) {
				substitution.put(letters.get(i), letterSequences[j].charAt(i));
			}
			System.out.println(substituteChars(letter, substitution));
			substitution.clear();
		}
	}

	public void createSubstitutionDictionary() {

	}

	public String substituteChars(char[] letters, Map<Character, Character> substitution) {
		StringBuilder result = new StringBuilder();
		for (char c : letters) {
			result.append(substitution.get(c));
		}
		return result.toString();
	}

	public Map<Character, Double> sortMapByFrequency(Map<Character, Double> map) {
		List<Entry<Character, Double>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<Character, Double> result = new LinkedHashMap<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			result.put(list.get(i).getKey(), list.get(i).getValue());
		}

		return result;
	}

}

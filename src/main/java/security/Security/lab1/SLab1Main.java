package security.Security.lab1;

public class SLab1Main {
	public static void main(String[] args) {
		Lab1Decoder decoder = new Lab1Decoder();
		decoder.decipherTextFromBinary("src/main/resources/data.bin");
		decoder.printDecodedText();
		//
		//System.out.println(EnglishTextAnalyzer.isEnglishTextQuotient("Write a code to attack some simple substitution cipher. To reduce the complexity of this one we will use only uppercase letters, so the keyspace is only 26! To get this one right automatically you will probably need to use some sort of genetic algorithm (which worked the best last year), simulated annealing or gradient descent. Seriously, write it right now, you will need it to decipher the next one as well. Bear in mind, there's no spaces. \r\n"
		//		+ "https://docs.google.com/document/d/1HY7Dl-5itYD3C_gkueBvvBFpT4CecGPiR30BsARlTpQ/edit?usp=sharing"));
		Part4 part4 = new Part4();
		part4.substitutionalDecoder();
	}
}

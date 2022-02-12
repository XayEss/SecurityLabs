package security.Security.lab1.geneticSubstitution;

import security.Security.lab1.EnglishTextAnalyzer;

public class GeneticMain {
	
	private static String text = "UMUPLYRXOYRCKTYYPDYZTOUYDZHYJYUNTOMYTOLTKAOHOKZCMKAVZDYBRORPTHQLSERUOERMKZGQJOIDJUDNDZATUVOTTLMQBOWNMERQTDTUFKZCMTAZMEOJJJOXMERKJHACMTAZATIZOEPPJKIJJNOCFEPLFBUNQHHPPKYYKQAZKTOTIKZNXPGQZQAZKTOTIZYNIUISZIAELMKSJOYUYYTHNEIEOESULOXLUEYGBEUGJLHAJTGGOEOSMJHNFJALFBOHOKAGPTIHKNMKTOUUUMUQUDATUEIRBKYUQTWKJKZNLDRZBLTJJJIDJYSULJARKHKUKBISBLTOJRATIOITHYULFBITOVHRZIAXFDRNIORLZEYUUJGEBEYLNMYCZDITKUXSJEJCFEUGJJOTQEZNORPNUDPNQIAYPEDYPDYTJAIGJYUZBLTJJYYNTMSEJYFNKHOTJARNLHHRXDUPZIALZEDUYAOSBBITKKYLXKZNQEYKKZTOKHWCOLKURTXSKKAGZEPLSYHTMKRKJIIQZDTNHDYXMEIRMROGJYUMHMDNZIOTQEKURTXSKKAGZEPLSYHTMKRKJIIQZDTNROAUYLOTIMDQJYQXZDPUMYMYPYRQNYFNUYUJJEBEOMDNIYUOHYYYJHAOQDRKKZRRJEPCFNRKJUHSJOIRQYDZBKZURKDNNEOYBTKYPEJCMKOAJORKTKJLFIOQHYPNBTAVZEUOBTKKBOWSBKOSKZUOZIHQSLIJJMSURHYZJJZUKOAYKNIYKKZNHMITBTRKBOPNUYPNTTPOKKZNKKZNLKZCFNYTKKQNUYGQJKZNXYDNJYYMEZRJJJOXMERKJVOSJIOSIQAGTZYNZIOYSMOHQDTHMEDWJKIULNOTBCALFBJNTOGSJKZNEEYYKUIXLEUNLNHNMYUOMWHHOOQNUYGQJKZLZJZLOLATSEHQKTAYPYRZJYDNQDTHBTKYKYFGJRRUFEWNTHAXFAHHODUPZMXUMKXUFEOTIMUNQIHGPAACFKATIKIZBTOTIKZNKKZNLORUKMLLFBUUQKZNLEOHIEOHEDRHXOTLMIRKLEAHUYXCZYTGUYXCZYTIUYXCZYTCVJOEBKOHE";
	private static String text2 = "Englishtextsforbeginnerstopracticereadingandcomprehensiononlineandforfree.PracticingyourcomprehensionofwrittenEnglishwillbothimproveyourvocabularyandunderstandingofgrammarandwordorder.Thetextsbelowaredesignedtohelpyoudevelopwhilegivingyouaninstantevaluationofyourprogress.";
	private static String lol = "I just returned from the greatest summer vacation! It was so fantastic, I never wanted it to end. I spent eight days in Paris, France. My best friends, Henry and Steve, went with me. We had a beautiful hotel room in the Latin Quarter, and it wasn’t even expensive. We had a balcony with a wonderful view.\r\n"
			+ "\r\n"
			+ "We visited many famous tourist places. My favorite was the Louvre, a well-known museum. I was always interested in art, so that was a special treat for me. The museum is so huge, you could spend weeks there. Henry got tired walking around the museum and said “Enough! I need to take a break and rest.”\r\n"
			+ "\r\n"
			+ "We took lots of breaks and sat in cafes along the river Seine. The French food we ate was delicious. The wines were tasty, too. Steve’s favorite part of the vacation was the hotel breakfast. He said he would be happy if he could eat croissants like those forever. We had so much fun that we’re already talking about our next vacation!";
	
	public static void main(String[] args) {
		Gene gene = new Gene("TEOANIHSDRLUCMFWYPVBGKJQXZ");
		Gene gene2 = new Gene();
		String phrase = "HELLOWORLDFROMALLTHELOVERSOFCHOCOLATEICECREAM";
		//lol = lol.replaceAll("[!.,\s]", "");
		//System.out.println(lol);
		//System.out.println(EnglishTextAnalyzer.isEnglishTextQuotientV2(phrase));
		GeneticDecryptor decryptor = new GeneticDecryptor();
		decryptor.decryptText(text);
//		System.out.println(gene.getKey() + " k2: " + gene2.getKey());
//		Gene g2 = decryptor.crossGenesv2(gene, gene2);
//		System.out.println("lol" + g2.getKey());
//		for(int i = 0; i < 50; i++) {
//		decryptor.tournament();
//		}
		//System.out.println("freq 1: " + EnglishTextAnalyzer.isEnglishTextQuotient(lol));
//		System.out.println("freq 2: " + EnglishTextAnalyzer.isEnglishTextQuotient(gene.encodeTextGene(phrase).toLowerCase()));
	}

}

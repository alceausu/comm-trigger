package alceausu.nlp.sense;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.semantics.type.SemanticField;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSegmenter;
import de.tudarmstadt.ukp.uby.resource.UbySemanticFieldResource;
import de.tudarmstadt.ukp.uby.uima.annotator.UbySemanticFieldAnnotator;

public class LinguisticAnnotationPipeline {

	private final AnalysisEngineDescription tokenizer;
	private final AnalysisEngineDescription posTagger;
	private final AnalysisEngineDescription lemmatizer;
	private final AnalysisEngineDescription semanticFieldAnnotator;

	public LinguisticAnnotationPipeline(String user, String pass)
			throws ResourceInitializationException {
		ExternalResourceDescription ubyRes = createExternalResourceDescription(
				UbySemanticFieldResource.class,
				UbySemanticFieldResource.PARAM_URL,
				"localhost/uby_medium_0_7_0",
				UbySemanticFieldResource.PARAM_DRIVER,
				"com.mysql.jdbc.Driver",
				UbySemanticFieldResource.PARAM_DRIVER_NAME, "mysql",
				UbySemanticFieldResource.PARAM_USERNAME, user,
				UbySemanticFieldResource.PARAM_PASSWORD, pass);
		
		tokenizer = createEngineDescription(ClearNlpSegmenter.class);
		posTagger = createEngineDescription(ClearNlpPosTagger.class, ClearNlpPosTagger.PARAM_PRINT_TAGSET, true);
		lemmatizer = createEngineDescription(ClearNlpLemmatizer.class);
		semanticFieldAnnotator = createEngineDescription(
				UbySemanticFieldAnnotator.class,
				UbySemanticFieldAnnotator.PARAM_UBY_SEMANTIC_FIELD_RESOURCE, ubyRes);
	}

	public String annotate(String text) throws UIMAException, IOException {

		StringBuilder stringBuilder = new StringBuilder();

		JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(text);
		jcas.setDocumentLanguage("en");

		SimplePipeline.runPipeline(jcas, tokenizer, posTagger, lemmatizer,
				semanticFieldAnnotator);

		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {

			List<Token> sentenceTokens = JCasUtil.selectCovered(jcas,
					Token.class, sentence);
			for (int i = 0; i < sentenceTokens.size(); i++) {
				Token token = sentenceTokens.get(i);

				List<SemanticField> semanticFieldAnnotations = JCasUtil
						.selectCovering(jcas, SemanticField.class,
								token.getBegin(), token.getEnd());
				for (int j = 0; j < semanticFieldAnnotations.size(); j++) {
					SemanticField semanticField = semanticFieldAnnotations
							.get(j);
					if (semanticField.getValue().equals("UNKNOWN")) {
						stringBuilder.append(token.getCoveredText() 
								+ "[" + token.getLemma().getValue() + "_"	+ token.getPos().getType().getShortName() + "] ");
					} else {
						stringBuilder.append(token.getCoveredText() 
								+ "[" + token.getLemma().getValue() + "_" + token.getPos().getType().getShortName() 
								+ ";" + semanticField.getValue() + "] ");
					}
				}

			}

		}

		return stringBuilder.toString();
	}
}



import edu.smu.tspell.wordnet.*;

/**
 
 */
public class testJAWS
{

	public String[]  WordnetSearch(String[] searchStrings)
	{
		String[] wordForms=null;;
		System.out.print("string=="+searchStrings);
		if (searchStrings.length > 0)
		{
			//  Concatenate the command-line arguments
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < searchStrings.length; i++)
			{
				buffer.append((i > 0 ? " " : "") + searchStrings[i]);
			}
			
			String wordForm = buffer.toString();
			
			wordForms = WordnetSearch(wordForm);
		}
		else
		{
			System.err.println("You must specify " +
					"a word form for which to retrieve synsets.");
		}
		return wordForms;
	}

	
	public String[]  WordnetSearch(String wordForm)
	{

		String[] wordForms=null;
			System.setProperty("wordnet.database.dir", "C:\\Program Files\\WordNet\\2.1\\dict");
			//  Get the synsets containing the word form
			WordNetDatabase database = WordNetDatabase.getFileInstance();
			Synset[] synsets = database.getSynsets(wordForm);
			//  Display the word forms and definitions for synsets retrieved
			//System.out.println("++++/////"+synsets.toString());
			if (synsets.length > 0)
			{
				System.out.println("The Wordnet contain '" +wordForm + "' or a possible base form " +"of that text:");
				System.out.println("++++/////"+synsets.toString());
				for (int i = 0; i < synsets.length; i++)
				{
					System.out.println("--------*****------");
					wordForms = synsets[i].getWordForms();
					for (int j = 0; j < wordForms.length; j++)
					{
						System.out.print((j > 0 ? ", " : "") +
								wordForms[j]);
					}
					System.out.println(": " + synsets[i].getDefinition());
				}
			}
			else
			{
				System.err.println("No Wordnet information exist that contain " +
						"the word form '" + wordForm + "'");
			}
			return wordForms;
	}
	
	public Synset[] WordnetSearchSyn(String wordForm)
	{
	
		/*System.setProperty("wordnet.database.dir", "C:\\Program Files\\WordNet\\2.1\\dict\\");
		//  Get the synsets containing the word form
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets=null;
		String[] searchStrings =wordForm.split(" ");
		for (int i = 0; i < searchStrings.length; i++)
		{
		synsets= database.getSynsets(searchStrings[i]);
		}
		return synsets;*/
		System.setProperty("wordnet.database.dir", "C:\\Program Files\\WordNet\\2.1\\dict\\");
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(wordForm);
		//  Display the word forms and definitions for synsets retrieved
		if (synsets.length > 0)
		{
			System.out.println("The following synsets contain '" +
					wordForm + "' or a possible base form " +
					"of that text:");
			for (int i = 0; i < synsets.length; i++)
			{
				System.out.println("");
				String[] wordForms = synsets[i].getWordForms();
				for (int j = 0; j < wordForms.length; j++)
				{
					System.out.print((j > 0 ? ", " : "") +
							wordForms[j]);
				}
				System.out.println(": " + synsets[i].getDefinition());
			}
		}
		else
		{
			System.err.println("No synsets exist that contain " +
					"the word form '" + wordForm + "'");
		}
		System.out.println("Array=="+synsets.toString());
		return synsets;
	}
	
		
	

	
	
	/*public String getWordFormat(String searchString)
	{
		String[] searchStrings =searchString.split(" ");
		String[] WordForms=null;
		
		String wordForm=null;
		if (searchStrings.length > 0)
		 {
			//  Concatenate the command-line arguments
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < searchStrings.length; i++)
			{
				buffer.append((i > 0 ? " " : "") + searchStrings[i]);
			}
			
			wordForm = buffer.toString();
			
		}
		else
		{
			System.err.println("You must specify " +
					"a word form for which to retrieve synsets.");
		}
		return wordForm;
	}*/
}


public class Classifier {
public String clasiification;
	
	public final String NavigationalClassifier  = "Navigational";
	public final String TransactionalClassifier  = "Transactional";
	public final String InformationalClassifier  = "Informational";
	
	public String classify(String Query)
	{
	  if(isInformational(Query))
	  {
		  return InformationalClassifier;
	  }
	  else if(isTransactional(Query))
	  {
		  return TransactionalClassifier;
	  }
	  else
		  return NavigationalClassifier;
	}

	private boolean isTransactional(String query) {
		
		if(query.contains("movie")||query.contains("song")||query.contains("lyric")||query.contains("download")||query.contains("audio")||query.contains("video")||query.contains("pictures")||query.contains("games")||query.contains("movie")||query.contains("movie"))
		{
			return true;
		}
		return false;
	}

	private boolean isInformational(String query) {
		if(query.contains("what")||query.contains("how")||query.contains("who")||query.contains("price")||query.contains("audio")||query.contains("get")||(query.split(" ").length >3))
		{
			return true;
		}
		return false;
	}

}

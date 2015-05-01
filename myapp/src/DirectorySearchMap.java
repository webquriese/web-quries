
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class DirectorySearchMap {
	
	ArrayList<String> Categories=null;
	String DirectorySearchURL = "http://dir.search.yahoo.com/search?fr=sfp&p=";
	String[] urls=new String[17];
	public String[] getUrls() {
		for(int i=0;i<16;i++)
		{
			
			System.out.println("urls="+urls[i]);
		}
		return urls;
	}

	public void setUrls(String[] urldata) {
		System.out.println();
		urls = urldata;
	}
	public DirectorySearchMap ()
	{
		//System.out.println("in directory map constructor....");
		Categories = new ArrayList<String>();
	}
	public ArrayList<String> directorySearch(String search)
	{
		Reader reader = null;
		URL yahoo = null;
		try {
			//yahoo = new URL(DirectorySearchURL + URLEncoder.encode(search));
			
			yahoo=new URL(DirectorySearchURL+URLEncoder.encode(search));
			//System.out.println("in directory map constructor...."+yahoo);
			//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.20.1", 3128));
			HttpURLConnection uc;
			
		
	        BufferedReader in;
	        String inputLine;
	       
	        	
	        	uc = (HttpURLConnection)yahoo.openConnection();
	        //	uc.setReadTimeout(15*1000);
	        	 uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.57 Safari/537.36"); 
				uc.connect();
//in=new BufferedReader(new InputStreamReader(yahoo.openStream()));	        
	in=new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF8"));
	        	//in = new BufferedReader(reader);
				//StringBuilder ba = new StringBuilder();
				StringBuffer ba=new StringBuffer();
	        	while ((inputLine = in.readLine()) != null) 
				{
					ba.append(inputLine+"\n");
				}
			//	System.out.println("String =="+ba.toString());
				String[] urldata = parserResults(ba.toString());
				for(int i=0;i<16;i++)
				{
					
					System.out.println("urldata="+urldata[i]);
				}
					
				setUrls(urldata);
		        in.close();
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}       
		        
		             Categories.remove("More...");
		        Categories.remove("Less");
		        Categories.remove("Last 3 months");
		        Categories.remove("Last 6 months");
		        Categories.remove("Past year");
	        
	               
	        

	        return Categories;
	}
	@SuppressWarnings("null")
	private String[] parserResults(String searchResult ) {
		Document d= Jsoup.parse(searchResult);
		StringBuilder [] urldata1=null;
	//	Elements filtered= d.getElementsByTag("ul");
		Elements filtered  =d.getElementsByClass("compList");
		List<Node> nodes =filtered.get(0).childNodes();
		
		System.out.println("Node size"+nodes.size());
		System.out.println("Node name="+nodes.toString());
		//System.out.println("Node nameee="+nodes.get(0).childNodes().toString());
		List<Node> nodes1=nodes.get(0).childNodes();
		//List<Node> nodes2=nodes1.get(0).childNodes();
		
		System.out.println("Node nameee="+nodes1.toString());
		System.out.println("Node1 size"+nodes1.size());
		//System.out.println("===="+nodes1.get(0).childNodes());
		
		System.out.println("===="+nodes1);
		//for(int i=0;i<filtered.size();i++)
		for(int i=0;i<nodes.size();i++)
		{
			System.out.println("inside for...");
			//List<Node> nodes =filtered.get(0).childNodes();
			if(nodes1.size() >0){
				//for(int k =0;k<nodes1.size() ;k++){
					
					//System.out.println("Node Name: "+nodes1.get(0).nodeName());
					//for(int j=0;j<nodes.get(0).childNodes().size();j++){
				for(int j=1;j<nodes1.size();j++){
						System.out.println("Node Name in for: "+ nodes1.get(j).childNode(0).nodeName());
						//System.out.println("Node class in for: "+ d.getElementsByClass("mb-10").toString());
						//if(nodes.get(0).childNode(0).nodeName()=="a"){
						if(nodes1.get(j).childNode(0).nodeName()=="a"){	
					System.out.println("Category : "+nodes1.get(j).childNode(0).childNode(0).toString());
					
							Categories.add(nodes1.get(j).childNode(0).childNode(0).toString());
							}
						//nodes.get(k).
					}
					}	}
		
		Element res  =d.getElementById("sidebar");
		Elements Ol= res.getElementsByTag("a");
		Elements resultmix = Ol.get(0).getElementsByClass("mb-10");
		
		
		System.out.println("res="+Ol.toString());
		//System.out.println("ol="+resultmix.toString());
		//Elements r=resultmix.get(0).childNode(0);
		
		System.out.println("olsize="+Ol.size());
		//StringBuilder urldata = new StringBuilder();
		int j=Ol.size()-5;
		String[] str=new String[j];
	//	ArrayList<String> str1=new ArrayList<String>();
		//for(int i=0;i<j;i++ )
		//{
		//str1.add(Ol.get(i).attr("href"));
		//}
		for(int i=0;i<j;i++ )
		{
			
			System.out.println("j="+j+"URL : "+Ol.get(i).attr("href").toString()+"i=="+i);
			str[i]=Ol.get(i).attr("href");
			
			
		}
		/*int i=0;
		do
		{
			System.out.println("j="+j+"URL : "+Ol.get(i).attr("href").toString()+"i=="+i);
			str[i]=Ol.get(i).attr("href").toString();
			i++;
			
		}while(j!=0);*/
		return str;
	}//end parse method
	}



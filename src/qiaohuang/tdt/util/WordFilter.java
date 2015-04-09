package qiaohuang.tdt.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.domain.Term;

import qiaohuang.tdt.conf.PathConfig;



public class WordFilter {
	
	
	HashSet<String> stopWords;//ͣ�ôʱ�
	HashSet<String> noiseWords;//�����ʱ�
	
	public WordFilter(){
		stopWords = new HashSet<String>();
		noiseWords = new HashSet<String>();
		
		//��ȡͣ�ôʱ�
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(PathConfig.StopWordsDicPath),"UTF-8"));  
			String line = null;  
			while ((line = br.readLine()) != null) {  
				line = line.trim();
				stopWords.add(line);  
				//System.out.println(line);
			}
			br.close();
		}
		catch (Exception e){
			
		}
		
		//System.out.println("ͣ�ôʸ����� "+stopWords.size());
		
		
		
		//��ȡ�����ʱ�
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(PathConfig.NoiseWordsDicPath),"UTF-8"));  
			String line = null;  
			while ((line = br.readLine()) != null) {  
				line = line.trim();
				noiseWords.add(line);  
				//System.out.println(line);
			}
			br.close();
		}
		catch (Exception e){
			
		}
		
		//add new noise words according to certain dataset
		//������ˡ�ĳ�ꡱ����ĳ�¡�����ĳ�ա�������ʽ��������
				
		for(int i=1500;i<=2015;i++)
			noiseWords.add(new Integer(i).toString()+"��");
		for(int i=1;i<=12;i++)
			noiseWords.add(new Integer(i).toString()+"��");
		for(int i=1;i<=31;i++)
			noiseWords.add(new Integer(i).toString()+"��");
		for(int i=0;i<26;i++){
			noiseWords.add(""+(char)('a'+i));
			noiseWords.add(""+(char)('A'+i));
		}
			
		
		//System.out.println("�����ʸ�����"+noiseWords.size());
		
	
	}
	
	private boolean isStopWord(String word){
		if(stopWords.contains(word))
			return true;
		return false;		
	}
	
	private boolean isNoiseWord(String word){
		if(noiseWords.contains(word))
			return true;
		return false;	
	}	
	
	
	public void addStopWord(String word){
		//�����ֶ����ͣ�ô� (�·��ֵ�������Ҳ�ֶ���ӵ����
		stopWords.add(word);
	}
	
	
	
	public String filterWord(Term token){
		
		if(token.getNatureStr().charAt(0)=='w')//this is punctuation
			return null;
		
		String word = token.getName();			
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(word);
        word = m.replaceAll("");
		
		if(isStopWord(word))
			return null;
		if(isNoiseWord(word))
			return null;
		if(word.length()<2)//���˳���Ϊ0��1�Ĵ�
			return null;		
		
		return word;
	}

}

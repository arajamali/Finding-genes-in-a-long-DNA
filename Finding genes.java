import edu.duke.*;
/**
 * In this class, the genes in a long DNA strand will be detected and stored.
 * Accordingly, cgratio and other parameters can easily be calculated
 * @author (Arya Shahdi) 
 * @version (version1)
 */
public class Part1 {
     int findStopCodon(String dna, int startIndex,String stopCodon){
        int stopCodon_indx=dna.indexOf(stopCodon,startIndex);
        if(stopCodon_indx==-1)
        {
            return -1;
        }
        while((stopCodon_indx-startIndex)%3!=0)
        {
            stopCodon_indx=dna.indexOf(stopCodon,stopCodon_indx+1);
            
            if(stopCodon_indx==-1)
            {
                return -1;
            }
        }
        return stopCodon_indx;
        }
    
    StorageResource getAllGenes(String dna){
        StorageResource sr=new StorageResource();
        int ATG_indx=0;
        int StopColon_indx=0;
        int TGA_indx=-1,TAG_indx=-1,TAA_indx=-1;
        
        while(ATG_indx!=-1)
        {
            ATG_indx=dna.indexOf("ATG",ATG_indx);
            if(ATG_indx==-1)
            {
                break;
            }
            TAA_indx=findStopCodon(dna, ATG_indx,"TAA");
            TGA_indx=findStopCodon(dna, ATG_indx,"TGA");
            TAG_indx=findStopCodon(dna, ATG_indx,"TAG");
            if(TAA_indx!=-1 || TAG_indx!=-1 || TGA_indx!=-1)
            {
                int min_indx,min_indx2,max_indx;
                min_indx=Math.min(TAA_indx,TAG_indx);
                if(min_indx!=-1)
                {
                    min_indx2=Math.min(min_indx,TGA_indx);
                    if(min_indx2!=-1)
                    {
                        StopColon_indx=  min_indx2;
                    }
                    else
                    {
                        StopColon_indx=min_indx;
                    }
                }
                else
                {
                    max_indx=Math.max(TAA_indx,TAG_indx);
                    if(max_indx!=-1)
                    {
                        min_indx2=Math.min(max_indx,TGA_indx);
                        if(min_indx2!=-1)
                        {
                            StopColon_indx=min_indx2;
                        }
                        else
                        {
                            StopColon_indx=max_indx;
                        }
                    }
                    else
                    {
                        if(TGA_indx!=-1)
                        {
                            StopColon_indx=TGA_indx;
                        }
                        else{
                            break; 
                        }
                    }
                }
                sr.add(dna.substring(ATG_indx,StopColon_indx+3));
            }
            else
            {
                break;
            } 
            ATG_indx=StopColon_indx+3;//It starts looksing after the first ATG
        }
    return sr;
    }


    void testgetAllGenes(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        dna=dna.toUpperCase();
        double check;
        int counter=0;
        int longest=0;
        StorageResource sr=new StorageResource();
        sr=getAllGenes(dna);
        for(String curr_string:sr.data())
        {
           System.out.println(curr_string);
           if(curr_string.length()>longest)
           {
               longest= curr_string.length(); 
           }
        }
    }
    
    double cgRatio (String strg){
        int indx_C=0,indx_G=0;
        int counter_C=0, counter_G=0;
        while(true)
        {
           if(indx_C!=-1)
           {
               indx_C=strg.indexOf("C",indx_C); 
           }   
           if(indx_G!=-1)
           {
               indx_G=strg.indexOf("G",indx_G);
           }
           if(indx_C!=-1 ||indx_G!=-1)
           {
               if(indx_C!=-1)
               {
                   counter_C=counter_C+1;
                   indx_C=indx_C+1;
               }
               if(indx_G!=-1)
               {
                   counter_G=counter_G+1;
                   indx_G=indx_G+1;
               }
           }
           else
           {
               break;
           }
        }
        return (Double.valueOf(counter_G+counter_C)/
        strg.length());
    }
    void testcgRatio (){
        System.out.println(cgRatio("ATGCCATAG"));
    }
}
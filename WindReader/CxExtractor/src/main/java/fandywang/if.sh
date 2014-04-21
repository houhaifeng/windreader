#!/bin/bash

find ./ -type f -name "*.java"|while read line ;

do
	
	   echo $line
	   
	      iconv -f GBK -t UTF-8 $line > ${line}.utf8
		  
		     mv $line ${line}.18030
			 
			    mv ${line}.utf8 $line
				
			done

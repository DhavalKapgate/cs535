CS535 Introduction to Data Mining
Fall 2016
SEMESTER LONG PROJECT
README FILE

Author: Dhaval Kapgate
email: dkapgat1@binghamton.edu

PURPOSE:
	Design a recommender system.The goal of a recommender system is that, after we have collected a certain amount of the user preference rating data, 
	the system is able to predict the rating value k of user i for item j if user i has not yet given such a rating. In other words, given such a partially 
	filled out matrix, a recommender system shall be able to fill out all the predicted rating values for those elements with the current value of 0.
	
FILES:
	Driver.java, the file containing all the functions including main to calculate prediction.
	train_all_txt.txt, the text file containing input for the recommender system
	README.txt, the file you are reading
	About project.pdf, word file containing documentation
	Files generated: result.txt, the output file containing the output of the system
	
TO COMPILE:
	javac *.java
	
TO RUN:
	java Driver
	
References:
	https://en.wikipedia.org/wiki/Recommender_system
	http://infolab.stanford.edu/~ullman/mmds/ch9.pdf
	http://www.cs.carleton.edu/cs_comps/0607/recommend/recommender/itembased.html
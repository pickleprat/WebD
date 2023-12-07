#!/bin/bash 
imgcount=1
for jpeg in ~/Downloads/*.jpg; do 
	mv $jpeg /home/pickleprat/Desktop/projects/webd/jdbc/FariDB/Images
	imgcount=$((imgcount+1))
	echo "Changed $jpeg.jpg -> image_$imgcount.jpg"
done 
echo $imgcount 

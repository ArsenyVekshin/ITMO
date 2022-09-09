cd ~
rm -rf lab0
mkdir lab0
cd lab0


#init non-dir files:
echo "Тип диеты Nullvore" >>cascoon3

echo "Способности Mud-Slap Water Gun Bide mud" >>marshtomp1
echo "Shot Foresight Mud Bomb Take Down Muddy Water Protect Earthquake" >>marshtomp1
echo "Endeavor" >>marshtomp1

echo -e "Способности Mountain Peak Strudy Sand\nRush" >>roggenrola3


#init dir glaceon6
mkdir glaceon6
cd glaceon6

echo -e "weight=19.8 height=20.0 atk=6\ndef=6" >>elgyem
echo -e "Развитые способности Weak Armor" >>dwebble
mkdir xatu poliwhirl

cd -



#init dir gothorita7
mkdir gothorita7
cd gothorita7

echo "Overland=4 Sky=8 Jump=2 Power=2 Intelligence=4 Sinker=0" >>yanma
echo -e "Тип \nдиеты Carnivore" >>spinarak
mkdir gliscor horsea

cd -



#init dir snover3
mkdir snover3
cd snover3

echo -e "Способности Landslide Unbreakable Sand Rush Sand\nForce" >>excadrill
echo -e "Развитые способности Infiltarator" >>zubat
echo -e "Развитые\nспособности Pickup Unnerve" >>persian

mkdir shelgon kricketune

cd -



#set access laws 				#rwx rwx rwx
chmod 644 ~/lab0/cascoon3		#110 100 100
chmod 444 ~/lab0/marshtomp1		#100 100 100
chmod 440 ~/lab0/roggenrola3	#100 100 000

chmod 573 ~/lab0/glaceon6			#101 111 011
chmod 400 ~/lab0/glaceon6/elgyem	#100 000 000
chmod 404 ~/lab0/glaceon6/dwebble	#100 000 100
chmod 753 ~/lab0/glaceon6/xatu		#111 101 011
chmod 770 ~/lab0/glaceon6/poliwhirl	#111 111 000

chmod 361 ~/lab0/gothorita7				#011 110 001
chmod 60 ~/lab0/gothorita7/yanma		#000 110 000
chmod 622 ~/lab0/gothorita7/spinarak	#110 010 010
chmod 330 ~/lab0/gothorita7/gliscor		#011 011 000
chmod 307 ~/lab0/gothorita7/horsea		#011 000 111

chmod 777 ~/lab0/snover3				#111 111 111
chmod 400 ~/lab0/snover3/excadrill		#100 000 000
chmod 623 ~/lab0/snover3/zubat			#110 010 011
chmod 660 ~/lab0/snover3/persian		#110 110 000
chmod 770 ~/lab0/snover3/kricketune		#111 111 000
#chmod 777 ~/lab0/snover3/shelgon		#111 111 111

chmod u=rwx ~/lab0/snover3/shelgon
chmod g=rwx ~/lab0/snover3/shelgon
chmod o=rwx ~/lab0/snover3/shelgon



# Copy + links
touch ~/lab0/glaceon6/poliwhirl/cascoon3
cp ~/lab0/cascoon3 ~/lab0/glaceon6/poliwhirl/cascoon3


cp -R ~/lab0/glaceon6 ~/lab0/glaceon6/poliwhirl/
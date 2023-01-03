# Les Réseaunnés

## Présentation

Merci de vous pencher sur le projet des Réseaunnés. Il s'agit d'un simulateur réseau réalisé dans le cadre des projets transversaux du S3 de notre licence informatique. 

Cette application permet de simuler un réseau local avec des ordinateurs, des commutateurs, des routeurs et les liaisons entre ces machines. 


## Installer le projet

Pour installer ce projet il vous suffit de télécharger le fichier Réseaunnés.exe qui se trouve dans le dossier Simulateur_Réseau, ainsi que le dossier img. Vous pouvez télécharger les 2 dans un même dossier compréssé en cliquant sur le bouton download en haud à droite, quand vous êtes dans le dossier Simulateur_Réseau.

L'application utilise des fichiers du dossier img, alors il faut IMPÉRATIVEMENT que le dossier img et le fichier Réseaunnés.exe soient au même niveau d'une arborescence. 

Exemple : 
- |Documents
- ---|Réseaunnés.exe
- ---|img
- |Téléchargements

## Mode d'emploi

- Pour commencer, vous pouvez placer deux ordinateurs sur le plan en cliquant sur le bouton "Ajouter un ordinateur". 

- Pour configurer une carte réseau sur un ordinateur, cliquez dessus et sur configuration. Une fenêtre s'ouvre où vous pouvez renseigner le nom que vous voulez donner à votre interface, son adresse IP, son masque et sa passerelle. Seule la passerelle n'est pas obligatoire et est initalisée à 0.0.0.0 de base.

- Cliquez ensuite sur le bouton + et fermez la fenêtre. 

**Note**: Un ordinateur et un commutateur ne peuvent avoir qu'une carte réseau et le routeur peut en avoir 2, qui ne doivent pas correspondre au même réseau. 

- Après avoir donné une carte réseau à 2 ordinateurs, vous pouvez les lier en cliquant sur "ajouter une liaison" puis sur les deux ordinateurs que vous voulez lier. Si vous voulez supprimer une liaison, supprimez simplement une carte réseau liée.

- Allez sur le terminal d'un des deux ordinateurs, puis lancez la requête ping *adresse IP de l'autre ordinateur* pour voir le ping, confirmant ou non la liaison des deux ordinateurs.

**Note** : Les autres commandes du terminal sont vues [plus bas.](#commandes-du-terminal)

- Maintenant que vous connaissez les bases, vous pouvez tester vos connaissances sur les réseaux en essayant de faire communiquer deux ordinateurs lointains, sur différents réseaux, grâce aux commutateurs et aux routeurs, sur lesquelles les cartes réseau s'implémentent de la même manière qu'avec les ordinateurs.




## Commandes du Terminal

- **ifconfig** : Affiche la configuration de la machine

- **traceroute "_adresse IP_"** : Permet d'obtenir la trace IP et MAC d'une requêtre ICMP de tous les équipements et le temps que la requête met à arriver à chaque équipemennt

- **ping "_adresse IP_"** : Envoie 4 requêtes ICMP à l'adresse renseignée

- **ip "_paramètre_"** : 
    - **route** : Affiche la table de routage
    - **route add "_adresse réseau/masque_" via "_passerelle_"** : Ajoute une entrée dans la table de routage
    - **route delete "_adresse réseau/masque_"** : Supprime l'entrée spécifiée dans la table de routage

- **clear mac-adress-table** : Vide la table mac de la machine

- **show mac-adress-table** : Affiche la table mac de la machine

- **arp** : Affiche la table arp de la machine 

- **arp -d** : Vide la table arp de la machine

- **arp -d "_adresse IP_"** : Retire l'adresse ip renseignée de la table arp de la machine

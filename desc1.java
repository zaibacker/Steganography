// STÉGANOGRAPHIE: PROGRAMME PERMETTANT DE DÉCOMPOSER UNE IMAGE STÉGANOGRAPHIÉE ET DE RETROUVER L'IMAGE CACHÉE


//***************************************************
/*                                                  *
 *             Programme Réalisé Par                *
 *                                                  *
 *		         ZAIBACKER                          * 
 *                                                  *
 *                                                  *
 ****************************************************/


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage.*;
import com.sun.imageio.plugins.png.*;



class desc1 extends Frame { 
  Image imagenFuente; 	// Image stéganographiée 
  Image imagenNueva;    // Image décodée            
               

  int iniAncho;			// largeur de l'image stéganographiée 
  int iniAlto;			// hauteur de l'image décodée
  


 // Valeurs de la bordure pour l'objet conteneur
  int insetArriba;
  int insetIzqda;
  
  // Méthode de contrôle du programme
  public static void main( String[] args ) {
    // On instancie un objet de la classe
    desc1 obj = new desc1();

    obj.repaint();
    }

  // Constructeur de la classe
  public desc1() {
    // On charge l'images depuis le fichier indiqué, qui est
    // suposé être situé dans le répertoire actuel du disque dur.
    imagenFuente = Toolkit.getDefaultToolkit().getImage( "imagenNueva1.png" ); //Nom de l'image à décrypter . A NE PAS MODIFIER LE NOM
    
    // On utilise un objet MediaTracker pour bloquer la tâche jusqù'à
    // que l'image se soit chargée ou qu'il se soit passé 10 secondes
    // depuis le début de la charge 
    MediaTracker tracker = new MediaTracker( this );
    tracker.addImage( imagenFuente,1 );
        
    try {
      if( !tracker.waitForID( 1,10000 ) ) {
        System.out.println( "Erreur de chargement de l'image" );
        System.exit( 1 );        
      }
    } catch( InterruptedException e ) {
      System.out.println( e );
      }
      
  

    iniAncho = imagenFuente.getWidth( this );
    iniAlto = imagenFuente.getHeight( this );
    // On affiche le Frame
    this.setVisible( true );
    

    insetArriba = this.getInsets().top;
    insetIzqda = this.getInsets().left;

    
    this.setSize( insetIzqda+iniAncho,insetArriba+iniAlto );
    this.setTitle( "Tutorial de Java, Gráficos" );
    this.setBackground( Color.yellow );    

   
    int[] pix = new int[iniAncho * iniAlto];//matrice contenant l'image décodée


    try {

      PixelGrabber pgObj = new PixelGrabber( imagenFuente,
        0,0,iniAncho,iniAlto,pix,0,iniAncho );
     

            
      if( (pgObj.grabPixels() && 
        ( (pgObj.getStatus() & ImageObserver.ALLBITS ) != 0 )) ) {

        for( int i=0; i < (iniAncho*iniAlto); i++ ) {
			pix[i] = pix[i] << 5;
	        pix[i] = pix[i] | 0xFF000000;
			pix[i] = pix[i] & 0xFFE0E0E0;          
          }
        }
      else {
        System.out.println( "Problemas al descomponer la imagen" );
        }
    } catch( InterruptedException e ) {
      System.out.println( e );
      }
    
    // On utilise la méthode createImage() pour obtenir une nouvelle
    // image à partir de l'array de pixels que nous avons modifié.
    imagenNueva = this.createImage( new MemoryImageSource(
      iniAncho,iniAlto,pix,0,iniAncho ) );
    

    this.addWindowListener(

      new WindowAdapter() {
        public void windowClosing( WindowEvent evt ) {
          // Fin du programme
          System.exit( 0 );
        }
      }
    );
  }
  
  
  public void paint( Graphics g ) {
    if( imagenNueva != null ) {

      g.drawImage( imagenNueva,insetIzqda,insetArriba,this );
      
    //Convertir le type Image en BufferedImage
    File outFile=new File("imageDécripte.png" );
    BufferedImage bufImage = new BufferedImage(imagenNueva.getWidth(null), imagenNueva.getHeight(null), BufferedImage.TYPE_INT_RGB);
    bufImage.getGraphics().drawImage(imagenNueva, 0, 0, null);
     	
     	//On enregistre sur le disque au format "png" l'image en mémoire qu'on appelera imagenDecripte
     	try {
        	if (!ImageIO.write(bufImage,"png",outFile))
            System.out.println("Format d'écriture non pris en charge" );
   			 } catch (Exception e) {
        	System.out.println("erreur dans l'enregistrement de l'image :" );
        	e.printStackTrace();
    								}
    		
      
      }
    }
  }



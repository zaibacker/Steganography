
// STÉGANOGRAPHIE: PROGRAMME PERMETTANT DE CACHER UNE IMAGE DANS UNE AUTRE


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



class stegano1 extends Frame { 
  Image imagenFuente;	 // image source 	
  Image imagenFuente2;   // image source cachée
  Image imagenNueva;     // Image créée           
               

  int iniAncho;			// largeur de l'image source
  int iniAlto;  		// hauteur de l'image source
    
  int iniLarg2;			// largeur de l'image source cachée
  int iniHaut2;			// hauteur de l'image source cachée


  // Valeurs de la bordure pour l'objet conteneur
  int insetArriba;
  int insetIzqda;
  
  // Méthode de contrôle du programme
  public static void main( String[] args ) {
    // On instancie un objet de la classe
    stegano1 obj = new stegano1();

    obj.repaint();
    }

  // Constructeur de la classe
  public stegano1() {
    // On charge les images depuis le fichier indiqué, qui sont 
    // suposées être situées dans le répertoire actuel du disque dur.
    imagenFuente = Toolkit.getDefaultToolkit().getImage( "test.jpg" );		//NOM DE L'IMAGE CONTENEUR 
    imagenFuente2 = Toolkit.getDefaultToolkit().getImage( "im.jpg" );		//NOM DE L'IMAGE À CACHER
    
    // On utilise un objet MediaTracker pour bloquer la tâche jusqù'à
    // que l'image se soit chargée ou qu'il se soit passé 10 secondes
    // depuis le début de la charge
    
    //tracker 1 
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
      
    //tracker 2
    MediaTracker tracker2 = new MediaTracker( this );
    tracker2.addImage( imagenFuente2,1 );
        
    try {
      if( !tracker2.waitForID( 1,10000 ) ) {
        System.out.println( "Erreur de chargement de l'image" );
        System.exit( 1 );        
      										}
    	} catch( InterruptedException e ) {
     	 System.out.println( e );
      										}   

    iniAncho = imagenFuente.getWidth( this );
    iniAlto = imagenFuente.getHeight( this );
    iniLarg2 = imagenFuente2.getWidth( this );
    iniHaut2 = imagenFuente2.getHeight( this ); 
    
    //si les 2 images pas de même taille, on redimensionne
    
    if((iniAncho!=iniLarg2) || (iniAlto!=iniHaut2 ))
    {
    	imagenFuente2 = imagenFuente2.getScaledInstance(iniAncho,
                               iniAlto,
                               Image.SCALE_DEFAULT);
    }
    
    // On affiche le Frame
    this.setVisible( true );
    

    insetArriba = this.getInsets().top;
    insetIzqda = this.getInsets().left;

    
    this.setSize( insetIzqda+iniAncho,insetArriba+iniAlto );
    this.setTitle( "Tutorial de Java, Gráficos" );
    this.setBackground( Color.yellow );    

   
    int[] pix = new int[iniAncho * iniAlto];  //matrice contenant l'image conteneur
    int[] pix2 = new int[iniAncho * iniAlto]; //matrice contenant l'image cachée

    //on transforme les images en matrice
    try {

      PixelGrabber pgObj = new PixelGrabber( imagenFuente,  
        0,0,iniAncho,iniAlto,pix,0,iniAncho );

      PixelGrabber pgObj2 = new PixelGrabber( imagenFuente2,
        0,0,iniAncho,iniAlto,pix2,0,iniAncho );          

            
      if( (pgObj.grabPixels() && 
        ( (pgObj.getStatus() & ImageObserver.ALLBITS ) != 0 )) && (pgObj2.grabPixels() && 
        ( (pgObj2.getStatus() & ImageObserver.ALLBITS ) != 0 )) ) {

        for( int i=0; i < (iniAncho*iniAlto); i++ ) {
			pix[i] = pix[i] & 0xFFF8F8F8;
			pix2[i] = pix2[i] >>> 5;
			pix2[i] = pix2[i] & 0xFF070707;
			pix[i] = pix[i] | pix2[i];         
          }
        }
      else {
        System.out.println( "Problèmes en décomposant l'image" );
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
    File outFile=new File("imagenNueva1.png" );
    BufferedImage bufImage = new BufferedImage(imagenNueva.getWidth(null), imagenNueva.getHeight(null), BufferedImage.TYPE_INT_RGB);
    bufImage.getGraphics().drawImage(imagenNueva, 0, 0, null);
     	
     	//On enregistre sur le disque au format "png" l'image en mémoire qu'on appelera imagenNueva1
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



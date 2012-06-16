/*     */ package ex6;
/*     */ 
/*     */ import ex6.models.Spaceship;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.media.opengl.GLCanvas;
/*     */ 
/*     */ public class App
/*     */ {
/*     */   private static Frame frame;
/*     */   private static GLCanvas canvas;
/*     */   private static GameLogic game;
/*     */   private static Viewer viewer;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  44 */     frame = new Frame("ex6: Asteroidbelt");
/*     */ 
/*  47 */     Spaceship spaceship = new Spaceship();
/*  48 */     game = new GameLogic(spaceship);
/*  49 */     viewer = new Viewer(game, spaceship);
/*     */ 
/*  52 */     canvas = new GLCanvas();
/*     */ 
/*  54 */     frame.setSize(500, 500);
/*  55 */     frame.setLayout(new BorderLayout());
/*  56 */     frame.add(canvas, "Center");
/*     */ 
/*  59 */     canvas.addGLEventListener(viewer);
/*  60 */     frame.addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e) {
/*  63 */         System.exit(1);
/*  64 */         super.windowClosing(e);
/*     */       }
/*     */     });
/*  67 */     canvas.addKeyListener(new KeyAdapter()
/*     */     {
/*     */       public void keyTyped(KeyEvent e)
/*     */       {
/*  71 */         if (e.getKeyChar() == 'r')
/*  72 */           App.game.restart();
/*  73 */         if (e.getKeyChar() == 'p')
/*  74 */           App.game.togglePause();
/*  75 */         if (e.getKeyChar() == 's')
/*  76 */           App.viewer.toggleShip();
/*  77 */         if (e.getKeyChar() == 'j')
/*  78 */           App.viewer.toggleProjection();
/*  79 */         if (e.getKeyChar() == 'm')
/*  80 */           App.viewer.toggleShipMark();
/*  81 */         if (e.getKeyChar() == 'g') {
/*  82 */           App.game.toggleGodMode();
/*     */         }
/*  84 */         super.keyTyped(e);
/*     */       }
/*     */ 
/*     */       public void keyPressed(KeyEvent e)
/*     */       {
/*  89 */         switch (e.getKeyCode()) {
/*     */         case 37:
/*  91 */           App.game.setTurnLeft(true);
/*  92 */           break;
/*     */         case 39:
/*  94 */           App.game.setTurnRight(true);
/*     */         case 38:
/*     */         }
/*  97 */         super.keyPressed(e);
/*     */       }
/*     */ 
/*     */       public void keyReleased(KeyEvent e)
/*     */       {
/* 102 */         switch (e.getKeyCode()) {
/*     */         case 37:
/* 104 */           App.game.setTurnLeft(false);
/* 105 */           break;
/*     */         case 39:
/* 107 */           App.game.setTurnRight(false);
/*     */         case 38:
/*     */         }
/* 110 */         super.keyReleased(e);
/*     */       }
/*     */     });
/* 116 */     frame.setVisible(true);
/* 117 */     canvas.requestFocus();
/* 118 */     canvas.requestFocusInWindow();
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.App
 * JD-Core Version:    0.6.0
 */
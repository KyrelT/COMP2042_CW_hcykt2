package test;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static com.sun.java.swing.plaf.motif.MotifBorders.FrameBorder.BORDER_SIZE;

public class Instructions extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String INSTRUCTIONS = "Instructions";
    private static final String BACK = "Back";
    private static final Color MENU_COLOR = new Color(100,205,150); // word color

    private boolean menuClicked;
    private boolean showInstructions;

    private Rectangle area;

    private static final Color BG_COLOR = new Color(30, 161, 161);
    private static final Color TEXT_COLOR = new Color(116, 52, 166);
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(255,255,255);
    private static final Color INNER_COLOR = new Color(255,255,255);

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private static final int DEF_MOVE_AMOUNT = 5;
    private final GameFrame owner;

    private Rectangle InstructionFace;
    private Rectangle backButton;

    private Timer Timer;

    private Font TitleFont;
    private Font backFont;
    private Font AFont;
    private Font DFont;
    private Font SpaceFont;

    public int xcoor=150;

    private Wall wall;
    private Rectangle player;

    private int strLen;

    public Instructions(GameFrame owner){

//        this.setFocusable(true);
//        this.requestFocusInWindow();
//
//        this.addMouseListener(this);
//        this.addMouseMotionListener(this);

        this.owner = owner;

        //this.startpoint = new Point(120,300);
        //makePlayer(playerPos);

        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));
        this.initialize();
        Timer = new Timer(10,e-> {
            // for every 10 secs, it will check for any updates in the game
            // for example if you change the value 10 into 1000 the updates on the game will be very slow
            if(player!=null) {
                player.setLocation(xcoor, 250);
            }

            repaint();

        });
        Timer.start();
//        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
//        menuButton = new Rectangle(btnDim);
        this.setPreferredSize(new Dimension(450,300));


        TitleFont = new Font("Noto Mono",Font.BOLD,30);
        AFont = new Font("Noto Mono",Font.BOLD,25);
        DFont = new Font("Noto Mono",Font.BOLD,25);
        SpaceFont = new Font("Noto Mono",Font.BOLD,25);
        backFont = new Font("Monospaced",Font.PLAIN,30);


    }

    private void drawPlayer(Graphics2D g2d){
        Rectangle player = makeRectangle(xcoor,250,150,10);
        this.player = player;
        g2d.setColor(new Color(200,200,244));
        g2d.fill(player);
    }
    private Rectangle makeRectangle(int x,int y,int width,int height){
        Point p = new Point((int)(x),y);
        return  new Rectangle(p,new Dimension(width,height));
    }

    public void paint(Graphics g){
        //Graphics2D g2d = (Graphics2D) g;
        drawInstructions((Graphics2D)g); // *********************************************************************************
    }


    public void drawInstructions(Graphics2D g2d){


        drawContainer(g2d); // *********************************************************************************
        drawText(g2d);
        drawPlayer(g2d);
        drawButton(g2d);


        //all the following method calls need a relative
        //painting directly into the HomeMenu rectangle,
        //so the translation is made here so the other methods do not do that.

//        Color prevColor = g2d.getColor();
//        Font prevFont = g2d.getFont();
//
//        double x = InstructionFace.getX();
//        double y = InstructionFace.getY();
//
//        g2d.translate(x,y);
//
//        //methods calls
//        drawText(g2d);
//        drawButton(g2d);
//        //end of methods calls
//
//        g2d.translate(-x,-y);
//        g2d.setFont(prevFont);
//        g2d.setColor(prevColor);
    }


    private void drawContainer(Graphics2D g2d){
        g2d.setColor(BG_COLOR); // background color
        Rectangle InstructionFace = new Rectangle(new Point(0, 0), new Dimension(450,300));
        g2d.fill(InstructionFace);

//        Color prev = g2d.getColor();
//
//        g2d.setColor(BG_COLOR);
//        g2d.fill(InstructionFace); // *********************************************************************************
//
//        Stroke tmp = g2d.getStroke();
//
//        g2d.draw(InstructionFace);
//
//        g2d.draw(InstructionFace);
//
//        g2d.setStroke(tmp);
//
//        g2d.setColor(prev);
    }

    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D TitleRect = TitleFont.getStringBounds(INSTRUCTIONS,frc);
        Rectangle2D ARect = AFont.getStringBounds("A To Move Left",frc);
        Rectangle2D DRect = DFont.getStringBounds("D To Move Right",frc);
        Rectangle2D SpaceRect = SpaceFont.getStringBounds("Spacebar To Pause the Game",frc);

        int sX = 120;
        int sY = 50;

        g2d.setFont(TitleFont);
        g2d.drawString(INSTRUCTIONS,sX,sY);

        sX = 30;
        sY = 100;

        g2d.setFont(AFont);
        g2d.drawString("A To Move Left",sX,sY);

        sY *= 1.5;

        g2d.setFont(DFont);
        g2d.drawString("D To Move Right",sX,sY);

        sY *= 1.3;

        g2d.setFont(SpaceFont);
        g2d.drawString("Spacebar To Pause the Game",sX,sY);

    }

    private void drawButton(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(backFont);
        g2d.setColor(MENU_COLOR);

        int x = 350;
        int y = 25;

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = backFont.getStringBounds(BACK,frc).getBounds().width;
        }

        if(backButton == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            backButton = backFont.getStringBounds(BACK,frc).getBounds();
            backButton.setLocation(x,y-backButton.height);
        }

        g2d.drawString(BACK,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);

//        x += backButton.x;
//        y += backButton.y + (backButton.height * 0.9);

//        if(menuClicked){
//            Color tmp = g2d.getColor();
//
//            g2d.setColor(CLICKED_BUTTON_COLOR);
//            g2d.draw(backButton);
//            g2d.setColor(CLICKED_TEXT);
//            g2d.drawString(BACK,x,y);
//            g2d.setColor(tmp);
//        }
//        else{
//            g2d.draw(backButton);
//            g2d.drawString(BACK,x,y);
//        }

    }


    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void drawInstructionMenu(Graphics2D g2d){
//        Font tmpFont = g2d.getFont();
//        Color tmpColor = g2d.getColor();
//
//
//        g2d.setFont(backFont);
//        g2d.setColor(MENU_COLOR);
//
//        if(strLen == 0){
//            FontRenderContext frc = g2d.getFontRenderContext();
//            strLen = backFont.getStringBounds(INSTRUCTIONS,frc).getBounds().width;
//        }


    }



    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                if (xcoor!=0) {
                    xcoor -= 10;
                }
                break;
            case KeyEvent.VK_D:
                if (xcoor!=300) {
                    xcoor += 10;
                }
                break;
            default:
                xcoor+=0;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showInstructions)
            return;
        if(backButton.contains(p)){
            showInstructions = false;
            //showHomeMenu = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
//        Point p = mouseEvent.getPoint();
//        if(backButton.contains(p)) {
//            menuClicked = true;
//            repaint(backButton.x, backButton.y, backButton.width + 1, backButton.height + 1);
//        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
//        if(menuClicked){
//            menuClicked = false;
//            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
//        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton != null && showInstructions) {
            if (backButton.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

}

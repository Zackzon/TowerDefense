 package Main

import Modes._
import Towers._
import Enemies._
import Graphics._

/*
 * Object that 
 */
object Cursor {
  
  var buildAllowed = true
  
  var selected: Int = 0
  
  // Keep example objects so we can get their values in clicked function
  var towerNum = Map[Int, Buildable](1 -> new BasicTower(Vector(0,0)),
                                     2 -> new LongTower(Vector(0,0)),
                                     3 -> new SprayTower(Vector(0,0)),
                                     4 -> new Fire(Vector(0,0)),
                                     5 -> new Ice(Vector(0,0))
                                    )
                                  
  /*
   * Checks if building to the cursors location is possible
   */
  def buildPossible: Boolean = {
    if (selected == 0) {false}
    else if(towerNum(selected).isInstanceOf[Towers]) {
      PlayMode.towers.forall(x => x.position != GameMap.getTile(main.mouseLocation()).location) && GameMap.getTile(main.mouseLocation()).solid
    }
    else {
      !GameMap.getTile(main.mouseLocation()).solid
    }
  }
  
  /*
   * 
   */
  def clicked() = {
    if(buildPossible) {
      selected match {
         // When no buildable is selected, do nothing
        case 0 =>
          
        case 1 => if (Player.money - towerNum(selected).cost >= 0 ) {
                  PlayMode.towers += new BasicTower(GameMap.getTile(main.mouseLocation()).location)
                  build(selected)
                  }
                  else {
                    noMoney
                  }
        
        case 2 => if (Player.money - towerNum(selected).cost >= 0 ) {
                  PlayMode.towers += new LongTower(GameMap.getTile(main.mouseLocation()).location)
                  build(selected)                  
                  }
                  else {
                    noMoney
                  }
        case 3 => if (Player.money - towerNum(selected).cost >= 0) {
                  PlayMode.towers += new SprayTower(GameMap.getTile(main.mouseLocation()).location)
                  build(selected)
                  }
                  else {
                    noMoney
                  }
        case 4 => if (Player.money - towerNum(selected).cost >= 0) {
                  PlayMode.abilities += new Fire(GameMap.getTile(main.mouseLocation()).location)
                  build(selected)
                  }
                  else {
                    noMoney
                  }
        case 5 => if (Player.money - towerNum(selected).cost >= 0) {
                  PlayMode.abilities += new Ice(GameMap.getTile(main.mouseLocation()).location)
                  build(selected)
                  }
                  else {
                    noMoney
                  }
      }
    }
    else if (selected != 0){
      PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Can't build here", 150,150,0,14)
    }
    
  }
  
  /*
   * Basic stuff involved in building that is same for all towers and abilities
   */
  def build(number :Int) = {
    Player.money -= towerNum(number).cost
    Player.moneySpent += towerNum(number).cost
    PlayMode.LargeTexts += new InfoText(Vector(670, 575), System.nanoTime(), "$ - " + towerNum(number).cost, 255,150,0,40)
  }
  
  /*
   * info text when player has no money and tries to build
   */
  def noMoney = PlayMode.texts += new InfoText(main.mouseLocation(), System.nanoTime(), "Not Enough money!", 255,0,0,14)
  
  /*
   * Indicates if cursor is hovering over any tower
   */
  def isOnTower: Boolean = {
    PlayMode.towers.exists( x => (x.position + main.offset).distanceToPoint(main.mouseLocation()) < 10  )
  }
  
   
  
  def draw = {
    if (selected != 0 && main.mouseLocation().y < 606) {  //Draws the preview of the building
      main.ellipseMode(2)
      if (buildPossible) {                                //If building is possible, draw the range
        main.pushMatrix()
        main.fill(0, 255, 0, 100)
        main.rect(GameMap.getTile(main.mouseLocation()).x*32, GameMap.getTile(main.mouseLocation()).y*32 ,32 ,32)
        main.ellipse(GameMap.getTile(main.mouseLocation).x*32+main.offset.x, GameMap.getTile(main.mouseLocation).y*32+main.offset.x,towerNum(selected).range.toFloat, towerNum(selected).range.toFloat)
        main.popMatrix()
      }
    }
  }
}
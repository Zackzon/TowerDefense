package Graphics

import scala.util._
import Main._

/*
 * Object to add small flying particles to the background
 */
object AnimatedBackground {
  
  var rand = new Random()
  
  // Array of particles that move in the background
  var stars = Array[Star]( new Star(Vector(-1,-1), Vector(-2,-2), 4), new Star(Vector(-1,-1), Vector(-2,-2),5),
                           new Star(Vector(-1, -1), Vector(-2,-2), 6),  new Star(Vector(-1, -1), Vector(-2,-2), 8))
  
  /*
   * Update the stars
   */
  def update = {
    for(s <- stars) {
      s.move()
      if(s.position.y < 0 || s.position.x < 0) { // if a star has flew past the screen, reset it with randomized values
        s.position = Vector(800 + rand.nextInt(1000), 670 + rand.nextInt(1000))
        s.direction = Vector(-rand.nextInt(300), -rand.nextInt(300))
        s.speed = rand.nextInt(8)+5
      }
    }
    
  }
  
  def draw = {
    stars.foreach(_.draw()) 
  }
}

/*
 * Class for small particles used in AnimatedBackground
 */
class Star(var position: Vector, var direction: Vector, var speed: Int) {
  
  def move() = {
    this.position += (direction - position).normalized()*speed
  }
  
  def draw() = {
    main.pushMatrix()
    main.stroke(200)
    main.strokeWeight(2)
    main.line(this.position.x, this.position.y, this.position.x + (direction - position).normalized().x*5, this.position.y + (direction - position).normalized().y*5)
    main.popMatrix()
  }
}
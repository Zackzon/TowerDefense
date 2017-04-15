package main

import scala.util._

class Mothership extends Enemy {
  maxHP = 1000
  HP = 400
  speed = 1.00
  reward = 30
 
  //Chooses a random spawn positon from all possible
  var rand2 = new Random(System.currentTimeMillis())
  var random_index = rand2.nextInt(GameMap.spawnerLocations.length)
  var result = GameMap.spawnerLocations(random_index)
  
  image_id = "mothership"
  var img2 = Sprites.get("mothershipDamaged").get

  position = result*32
  
  def draw(scale: Int) = {
	  if (frame > 0) {
	    main.image(img2,this.position.x, this.position.y )
	  }
	  else {
	    main.image(image, this.position.x, this.position.y)
	  }
	}
  
}
package myservermod;

import com.youthdigital.servermod.game.*;

public class Team extends TeamData {
  
  // points to win
  public int pointsToWin = 100;
  
  public Team(String blockName, String teamDisplayName, ChatColors teamColor) {
    super(blockName, teamDisplayName, teamColor);
  }
  
  @Override
  public void onUpdate() {
    
/* MOD POINTS */
    
    // zombie
    if (Conditions.mobDestroyed(EntityZombie.class)) {
      points += 3;
    }
    
    // skeleton
    if (Conditions.mobDestroyed(EntitySkeleton.class)) {
      points += 2;
    }
    
    // blaze
    if (Conditions.mobDestroyed(EntityBlaze.class)) {
      points += 5;
    }
    
    // creeper
    if (Conditions.mobDestroyed(EntityCreeper.class)) {
      points += 5;
    }
    
    // player
    if (Conditions.playerDied()) {
      points -= 5;
    }
    
    // chests
    points += Actions.updateScoreFromChestItemCount(getTeamName() + "Chest", Items.diamond, 5);
    Actions.clearChest(getTeamName() + "Chest");
    
   // game over
   if (points >= pointsToWin) {
     GameManager.triggerGameOver();
     Actions.spawnFireworks("fireworks", getTeamRGBColor());
     Actions.displayTitleToAll(getTeamChatColor() + "Game Over", getTeamDisplayName() + " is the winner!");
   }
    
  }
  
  @Override
  public void onResetGameToLobby() {
    
    Actions.displayChatMessageToAll(getTeamChatColor() + getTeamDisplayName() + ChatColors.WHITE + " Points:" + points);
    points = 0;
    
  }
  
  @Override
  public void onStartGame() {
    
  }
  
}
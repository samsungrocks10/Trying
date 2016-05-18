package myservermod;



import com.youthdigital.servermod.game.*;

public class Player extends PlayerData {
  
  /* VARIABLES */
  public int credits = 1;
  
  public Player(EntityPlayer parPlayerObject) {
    super(parPlayerObject);
  }
  
  @Override
  public void onUpdate() {
    
    /* SPECIAL BLOCKS */
    
    // join blocks
    if (Conditions.didRightClickBlock("redTeamJoin")) {
      GameManager.joinTeam("redTeam");
      Actions.displayChatMessageToAll(Actions.getPlayerName() + " has joined the" + getTeamChatColor() + " red team");
      Actions.startCountdown(30);
      Actions.playSound("redTeamJoin", "note.pling");
    }
    
    if (Conditions.didRightClickBlock("blueTeamJoin")) {
      GameManager.joinTeam("blueTeam");
      Actions.displayChatMessageToAll(Actions.getPlayerName() + " has joined the" + getTeamChatColor() + " blue team");
      Actions.startCountdown(30);
      Actions.playSound("blueTeamJoin", "note.pling");
    }
    
    // healing blocks
    if (Conditions.isStandingOnBlock("health") && Conditions.secondsGoneBy(1)) {
      Actions.restoreHealth(3);
      Actions.restoreHunger(3);
      Actions.spawnParticles("health", EnumParticleTypes.PORTAL, 50);
    }
    
/* STORE */
    
    // knight
    if (Conditions.didRightClickBlock("knight") && credits >= 1) {
      Actions.giveItems(Items.iron_sword, Enchantment.unbreaking, 3, Enchantment.knockback, 1,
                        Items.iron_helmet, Enchantment.unbreaking, 2, Enchantment.fireProtection, 1,
                        Items.iron_chestplate, Enchantment.unbreaking, 2, Enchantment.fireProtection, 1,
                        Items.iron_leggings, Enchantment.unbreaking, 2, Enchantment.fireProtection, 1,
                        Items.iron_boots, Enchantment.unbreaking, 2, Enchantment.fireProtection, 1,
                        Potion.moveSlowdown, 10000, 1,
                        Items.iron_pickaxe);
      Actions.playSound("knight", "random.successful_hit");
      Actions.displayChatMessage("A knight and shiny armor!");
      credits = credits - 1;
    }
    
    // archer
    if (Conditions.didRightClickBlock("archer")&& credits >= 1) {
      Actions.giveItems(Items.bow, Enchantment.infinity, 3, Enchantment.punch, 1, Items.arrow, 64,
                        Items.chainmail_helmet, Enchantment.unbreaking, 2,
                        Items.chainmail_chestplate, Enchantment.unbreaking, 2,
                        Potion.moveSpeed, 10000, 0,
                        Items.iron_pickaxe);
      Actions.playSound("archer", "random.successful_hit");
      Actions.displayChatMessage("Aim true, archer!");
      credits = credits - 1; 
    }
    
     // golden
     if (Conditions.didRightClickBlock("golden") && credits >= 1) {
       Actions.giveItems(Items.golden_sword, Enchantment.fireAspect, 2, Enchantment.unbreaking, 10, Enchantment.sharpness, 1,
       Items.golden_apple, 5, Items.golden_helmet, Enchantment.unbreaking, 10, Items.golden_chestplate, Enchantment.unbreaking, 10,
       Items.golden_leggings, Enchantment.unbreaking, 10, Items.golden_boots, Enchantment.unbreaking, 10, 
       Items.iron_pickaxe, Enchantment.unbreaking, 10, Enchantment.fortune, 1, Potion.moveSlowdown, 10000, 0);
       Actions.playSound("golden", "random.successful_hit");
       Actions.displayChatMessage("Ooo shiny");
       credits = credits - 1;
     }
    
    // leather
    if (Conditions.didRightClickBlock("leather") && credits >= 1) {
      Actions.giveItems(Items.stone_sword, Items.leather_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots,
      Items.bow, Potion.moveSpeed, 10000, 0, Items.iron_pickaxe, Enchantment.efficiency, 1, Items.arrow, 5);
      Actions.playSound("leather", "random.successful_hit");
      Actions.displayChatMessage("Kill the skeleton");
      credits = credits - 1;
    }
      
    
/* DISPLAY */ 
    if (credits > 0) {
      Actions.displaySmallInfo("Credits: " + credits);
    }
    if (GameManager.isGameActive()) {
      if (Game.secondsLeft > 9) {
        Actions.displaySmallInfo(getTeamChatColor() + getTeamDisplayName() + " - " + Game.minutesLeft + ":" + Game.secondsLeft);
      } else {
        Actions.displaySmallInfo(getTeamChatColor() + getTeamDisplayName() + " - " + Game.minutesLeft + ":0" + Game.secondsLeft);
      }
      
    } 
    
/* ITEM DROPS*/    
    
    if (Conditions.playerDied()) {
      Actions.dropItems(Items.diamond);
    }
    
    
/* NOTIFICATIONS */ 
    
    if (Conditions.playerDied()) {
      Actions.displayChatMessage("-5 points");
    }
    
    if (Conditions.mobDestroyed(EntityZombie.class)) {
      Actions.displayChatMessage("+3 points");
    }
    
    if (Conditions.mobDestroyed(EntitySkeleton.class)) {
      Actions.displayChatMessage("+2 points");
    }
    
    if (Conditions.mobDestroyed(EntityBlaze.class)) {
      Actions.displayChatMessage("+5 points");
    }
    
    if (Conditions.mobDestroyed(EntityCreeper.class)) {
      Actions.displayChatMessage("+5 points");
    }
    
    if (Conditions.itemsInInventory(Items.diamond, 5)) {
      Actions.displayChatMessage("Diamonds full!");
    }
    
    
/* EASTER EGGS */   
    
    if (Conditions.didRightClickBlock("clickEgg")) {
      Actions.giveItems(Items.diamond_sword, Enchantment.knockback, 4);
      Actions.displayChatMessage("You found a Easter Egg!");
      Actions.playSound("clickEgg", "random.orb");
      Actions.spawnParticles("clickEgg", EnumParticleTypes.SMOKE_LARGE, 100);
    }
    
    if (Conditions.isSneakingOnBlock("sneakEgg")) {
      Actions.givePotion(Potion.moveSpeed, 20, 5);
      Actions.displayChatMessage("You found a Easter Egg!");
      Actions.playSound("sneakEgg", "random.orb");
      Actions.spawnParticles("sneakEgg", EnumParticleTypes.SMOKE_LARGE, 100);
    }
    
    if (Conditions.isArrowInArea("arrowEgg", 4)) {
      Actions.spawnEntity("arrowEgg", EntityChicken.class, 50);
      Actions.displayChatMessage("You found a Easter Egg!");
      Actions.playSound("arrowEgg", "random.orb");
      Actions.spawnParticles("arrowEgg", EnumParticleTypes.SMOKE_LARGE, 100);
    }
    
    if (Conditions.didRightClickBlock("signEgg")) {
      Actions.giveItems(Items.iron_sword, Enchantment.sharpness, 1);
      Actions.displayChatMessage("You found a Easter Egg!");
      Actions.playSound("signEgg", "random.orb");
      Actions.spawnParticles("signEgg", EnumParticleTypes.SMOKE_LARGE, 100);
    }
    
    
/* CHEATS */
    
   if (Conditions.cheatEntered("suitUp")) {
     Actions.giveItems(Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots);
     Actions.displayChatMessage("suitUp cheat activated!");
   }
    
    if (Conditions.cheatEntered("pointParty")) {
      getTeam().points += GameManager.getValueFromCheat(1);
      Actions.displayChatMessage("pointParty cheat activated!");
    }
    
    if (Conditions.cheatEntered("ninjaSpeed")) {
      Actions.givePotion(Potion.moveSpeed, GameManager.getValueFromCheat(1), GameManager.getValueFromCheat(2));
      Actions.givePotion(Potion.jump, GameManager.getValueFromCheat(1), GameManager.getValueFromCheat(2));
      Actions.displayChatMessage("ninjaSpeed cheat activated!");
    } 
    
/* SECRET TELEPORT */    
    
    if (Conditions.isStandingOnBlock("secret")) {
      Actions.teleportPlayers(getTeamBase());
    }
    
    if (Conditions.isStandingOnBlock("redTeleport")) {
      Actions.teleportPlayers("redTPed");
    }
    
    if (Conditions.isStandingOnBlock("blueTeleport")) {
      Actions.teleportPlayers("blueTPed");
    }
    
    
  }
  
  @Override
  public void onJoinedServer(){
    
    Actions.teleportPlayers("lobbySpawn");
    
/* INSTRUCTIONS */
    Actions.displayChatMessage("How to Play");
    Actions.displayChatMessage("==================");
    Actions.displayChatMessage("Get a kit and join a team");
    Actions.displayChatMessage("Bring diamonds back to your chest and fight mobs");
    Actions.displayChatMessage("100 points to win");
    
  }
  
  @Override
  public void onStartGame() {
    Actions.teleportPlayers(getTeamBase());
    Actions.playSoundAtPlayersWithVolumeAndPitch("random.levelup", 0.5F, 0.5F);
  }
  
  @Override
  public void onResetGameToLobby() {
    
    credits = 1;
    Actions.restoreHealth(20);
    Actions.restoreHunger(20);
    Actions.clearPotions();
    Actions.removeItems();
    Actions.teleportPlayers("lobbySpawn");
    
    
  }
  
  @Override
  public void onRespawned() {
    
    Actions.teleportPlayers(getTeamBase());
    
  }
  
}
NOTE: I'll depreciate this if Immersive Enchanting adds compatibility with Apotheosis natively; this is just a temporary "fix".

This mod has two main features:

**1.** Prioratizes Immersive Enchanting's GUI over Apothic Enchanting.

  **1.1** Just Immersive Enchanting and Apotheosis
  > <img width="607" height="672" alt="2026-05-08_12 53 29" src="https://github.com/user-attachments/assets/a6a9d27f-efdf-4093-8070-b4de3f268aab" />


  1.2 Using Immersive Apothic mod alongside Immersive Enchanting and Apotheosis
  > <img width="794" height="742" alt="2026-05-08_12 52 13" src="https://github.com/user-attachments/assets/8465255f-d5eb-448e-bfe8-bbd798b13a4c" />



**2.** Adjusts Enchantment Costs for various vanilla + modded enchantments.

Vanilla & Modded Enchantments follow this formula I've done a few mods but if there's any missing it's easy to setup a datapack just follow the steps on Immersive Enchanting's https://immersive-enchanting.fandom.com/wiki/For_Modpack_Developers

```json
 COST_PER_LEVEL = {
  "item": 
  "minecraft:amethyst_shard", 
  "amount": 3, <<<<<<<<<<<<<<<< Increased by 3 per level
  "xp_levels": 5 <<<<<<<<<<<<<< Increased by 5 per level
  }
```

package cvut.cz.items;


import java.util.Map;

/**
 *  Keeps information about an item in the game.
 *
 * @param name The name of the item.
 * @param canBeCombinedWithInto A map defining the items this item can be combined with and the resulting item.
 *                              The key represents the name of the item it can be combined with.
 *                              The value represents the resulting item's name.
 * @param canBeEquipped Indicates whether the item can be equipped.
 * @param canBeUsed Indicates whether the item can be used.
 * @param canBeDiscarded Indicates whether the item can be discarded.
 * @param damage The damage value of the item, if applicable. If item should heal, this value must be negative.
 */
public record ItemInformation(
        String name,
        Map<String, String> canBeCombinedWithInto,
        boolean canBeEquipped,
        boolean canBeUsed,
        boolean canBeDiscarded,
        int damage)  {}

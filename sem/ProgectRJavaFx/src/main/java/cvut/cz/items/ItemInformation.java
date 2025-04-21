package cvut.cz.items;


import java.util.Map;

public record ItemInformation(String name, Map<String, String> canBeCombinedWithInto, boolean canBeEquipped, boolean canBeUsed, boolean canBeDiscarded, int damage)  {}

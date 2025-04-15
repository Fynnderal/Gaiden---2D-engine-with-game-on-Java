package cvut.cz.items;


import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;

import java.util.Map;

public record ItemInformation(GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, String name, Map<String, String> canBeCombinedWithInto, boolean canBeEquipped, boolean canBeUsed, boolean canBeDiscarded) {

    ItemInformation(GameSpriteSourceInformation gameSpriteSourceInformation, String name, Map<String, String> canBeCombinedWithInto, boolean canBeEquipped, boolean canBeUsed, boolean canBeDiscarded) {
        this(gameSpriteSourceInformation, new GameSpriteRenderInformation(0, 0, 0, 0,0, 0),  name, canBeCombinedWithInto,  canBeEquipped,  canBeUsed,  canBeDiscarded);
    }

}

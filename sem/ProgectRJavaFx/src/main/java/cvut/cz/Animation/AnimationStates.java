package cvut.cz.Animation;

/**
 * Enum representing the various animation states for characters in the game.
 * Each state corresponds to a specific action or posture of the character in different directions.
 */
public enum AnimationStates {
    IdleUp,
    IdleDown,
    IdleLeft,
    IdleRight,

    WalkingUp,
    WalkingDown,
    WalkingLeft,
    WalkingRight,

    ChasingUp,
    ChasingDown,
    ChasingLeft,
    ChasingRight,

    AttackingUp,
    AttackingDown,
    AttackingLeft,
    AttackingRight,

    ShootingPistolRight,
    ShootingPistolLeft,
    ShootingPistolUp,
    ShootingPistolDown,

    AimingPistolUp,
    AimingPistolDown,
    AimingPistolRight,
    AimingPistolLeft,

    ShootingShotGunUp,
    ShootingShotGunDown,
    ShootingShotGunLeft,
    ShootingShotGunRight,

    AimingShotGunUp,
    AimingShotGunDown,
    AimingShotGunLeft,
    AimingShotGunRight,

    DyingUp,
    DyingDown,
    DyingLeft,
    DyingRight,

    TakingDamageUp,
    TakingDamageDown,
    TakingDamageLeft,
    TakingDamageRight,
}

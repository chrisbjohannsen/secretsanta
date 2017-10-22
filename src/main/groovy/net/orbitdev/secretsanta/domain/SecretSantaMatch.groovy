package net.orbitdev.secretsanta.domain

import java.time.ZonedDateTime

/**
 * Match model
 */
class SecretSantaMatch {

    FamilyMember giver
    FamilyMember receiver
    ZonedDateTime matchDate
}

// Thêm settings mới cho anti-detection
private val humanizeAttacks by boolean("HumanizeAttacks", true)
private val randomizeTiming by boolean("RandomizeTiming", true)
private val smartAngles by boolean("SmartAngles", true)
private val silentRotation by boolean("SilentRotation", true)
private val randomizeHits by boolean("RandomizeHits", true)

// Thêm biến để theo dõi timing
private var lastAttackTime = 0L
private var nextDelayMs = 0L
private var failedAttacks = 0

// Cải thiện attack pattern
private fun updateAttackPattern() {
    if (!humanizeAttacks) return
    
    if (randomizeTiming) {
        // Thêm độ trễ ngẫu nhiên giữa các đòn đánh
        nextDelayMs = (50..150).random().toLong()
        
        // Thỉnh thoảng bỏ qua đòn đánh để giống người thật
        if (Math.random() < 0.1) {
            failedAttacks++
            return
        }
    }

    if (smartAngles) {
        // Giới hạn góc xoay tối đa mỗi tick
        val maxRotationSpeed = (8f..15f).random()
        options.maxTurnSpeed = maxRotationSpeed
    }
}

// Cải thiện rotation
private fun updateSmartRotation(target: EntityLivingBase) {
    if (!silentRotation) return

    // Thêm độ nhiễu nhỏ vào rotation
    val noiseX = (-2f..2f).random() 
    val noiseY = (-1f..1f).random()

    // Giới hạn góc nhìn trong phạm vi hợp lý
    val limitedYaw = mc.thePlayer.rotationYaw + noiseX
    val limitedPitch = (mc.thePlayer.rotationPitch + noiseY).coerceIn(-90f, 90f)

    // Áp dụng rotation một cách mượt mà
    setTargetRotation(Rotation(limitedYaw, limitedPitch))
}

// Cải thiện hit registration
private fun improveHitRegistration(target: EntityLivingBase) {
    if (!randomizeHits) return

    // Thay đổi vị trí hit ngẫu nhiên trên hitbox
    val hitX = target.posX + (-0.5..0.5).random()
    val hitY = target.posY + (0.0..target.height).random() 
    val hitZ = target.posZ + (-0.5..0.5).random()

    // Thỉnh thoảng miss một vài hit
    if (Math.random() < 0.05) {
        return
    }
}

// Cập nhật main attack function
private fun runAttack(isFirstClick: Boolean, isLastClick: Boolean) {
    updateAttackPattern()
    
    // Check timing
    if (System.currentTimeMillis() - lastAttackTime < nextDelayMs) {
        return  
    }

    val target = target ?: return
    
    updateSmartRotation(target)
    improveHitRegistration(target)

    // Rest of original attack code
    ...

    lastAttackTime = System.currentTimeMillis()
}

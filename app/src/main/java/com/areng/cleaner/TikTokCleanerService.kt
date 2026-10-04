package com.areng.cleaner

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class TikTokCleanerService : AccessibilityService() {

    companion object {
        var isRunning = false
        var deletedCount = 0
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isRunning) return

        val rootNode = rootInActiveWindow ?: return

        val shareNodes = rootNode.findAccessibilityNodeInfosByViewId("com.zhiliaoapp.musically:id/share_icon")
        val repostTextNodes = rootNode.findAccessibilityNodeInfosByText("You reposted")
        val repostTextNodesId = rootNode.findAccessibilityNodeInfosByText("Anda memosting ulang")

        if (repostTextNodes.isNotEmpty() || repostTextNodesId.isNotEmpty()) {
            val removeRepostBtn = rootNode.findAccessibilityNodeInfosByText("Remove repost")
                .ifEmpty { rootNode.findAccessibilityNodeInfosByText("Hapus posting ulang") }

            if (removeRepostBtn.isNotEmpty()) {
                removeRepostBtn[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                deletedCount++
                Thread.sleep(1500)
            } else {
                if (shareNodes.isNotEmpty()) {
                    shareNodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
        }
    }

    override fun onInterrupt() {
        isRunning = false
    }
}

package com.gh01l5.wechatmagician.spellbook.parser

/**
 * Dex 格式的文件头
 *
 * Refer: https://source.android.com/devices/tech/dalvik/dex-format
 */
@ExperimentalUnsignedTypes
class DexHeader {
    var version: Int = 0

    var checksum: Int = 0

    var signature: ByteArray = ByteArray(kSHA1DigestLen)

    var fileSize: Int = 0

    var headerSize: Int = 0

    var endianTag: Int = 0

    var linkSize: Int = 0

    var linkOff: Int = 0

    var mapOff: Int = 0

    var stringIdsSize: Int = 0

    var stringIdsOff: Int = 0

    var typeIdsSize: Int = 0

    var typeIdsOff: Int = 0

    var protoIdsSize: Int = 0

    var protoIdsOff: Int = 0

    var fieldIdsSize: Int = 0

    var fieldIdsOff: Int = 0

    var methodIdsSize: Int = 0

    var methodIdsOff: Int = 0

    var classDefsSize: Int = 0

    var classDefsOff: Int = 0

    var dataSize: Int = 0

    var dataOff: Int = 0

    /**
     * @suppress
     */
    companion object {
        const val kSHA1DigestLen = 20
        const val kSHA1DigestOutputLen = kSHA1DigestLen * 2 + 1
    }
}
package com.sample.characterviewer.network.response

data class SrcOptions(
    val directory: String,
    val is_fanon: Int,
    val is_mediawiki: Int,
    val is_wikipedia: Int,
    val language: String,
    val min_abstract_length: String,
    val skip_abstract: Int,
    val skip_abstract_paren: Int,
    val skip_end: String,
    val skip_icon: Int,
    val skip_image_name: Int,
    val skip_qr: String,
    val source_skip: String,
    val src_info: String
)
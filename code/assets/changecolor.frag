/**************************************************************************
 *
 * Copyright (c) 2014 MediaTek Inc. All Rights Reserved.
 * --------------------
 * This software is protected by copyright and the information contained
 * herein is confidential. The software may not be copied and the information
 * contained herein may not be used or disclosed except with the written
 * permission of MediaTek Inc.
 *
 ***************************************************************************/
/** \file
 * A fragment shader for testing texture layers
 *
 */

precision mediump float;

uniform sampler2D u_texture;
varying vec2 v_pos;
uniform float u_color_rgb;
uniform float u_m_opacity;
const float twoPi = 6.2832;
void main()
{
     //gl_FragColor = vec4(1.0,0.0,0.0,1.0);
	gl_FragColor = texture2D( u_texture, v_pos );
 	gl_FragColor.rgb *=u_color_rgb;
   gl_FragColor.a *= u_m_opacity;   
}
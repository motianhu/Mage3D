/**************************************************************************
 *
 * Copyright (c) 2013 MediaTek Inc. All Rights Reserved.
 * --------------------
 * This software is protected by copyright and the information contained
 * herein is confidential. The software may not be copied and the information
 * contained herein may not be used or disclosed except with the written
 * permission of MediaTek Inc.
 *
 ***************************************************************************/
/** \file
 * A fragment shader for quads with textures swaying from side to side
 *
 */

precision mediump float;

/* Material uniforms */
uniform sampler2D u_m_diffuseTexture;
uniform vec4 u_m_diffuseColour;
uniform float u_m_opacity;

varying vec2 v_texcoord;      // Adjusted texture coordinate

void main()
{
  gl_FragColor = u_m_diffuseColour *
    texture2D( u_m_diffuseTexture, v_texcoord );
  gl_FragColor.a *= u_m_opacity;
}

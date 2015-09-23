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
 * A vertex shader for silly effect
 *
 */

precision mediump float;

attribute vec4 a_position;    // Vertex position (model space)
attribute vec2 a_uv0;         // Texture coordinate

uniform highp mat4 u_t_modelViewProjection; // Model to projection space transform

varying vec2 v_pos;

void main()
{
  gl_Position = u_t_modelViewProjection * a_position;

  v_pos.x = a_uv0.x ;
  v_pos.y = 1.0-a_uv0.y;
}
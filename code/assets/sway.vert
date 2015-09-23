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
 * A vertex shader for quads with textures swaying from side to side
 *
 */
/* Transformation uniforms */
uniform mat4 u_t_modelViewProjection;
uniform vec4 u_uvCoordOffsetScale;

uniform float u_time;

uniform float u_swaySpeed;
uniform float u_swayDepth;
uniform float u_swayPhase;

attribute vec4 a_position;    // Vertex position (model space)
attribute vec2 a_uv0;         // Texture coordinate

varying vec2 v_texcoord;      // Adjusted texture coordinate

void main()
{
  // Calculate the screen-space vertex coordinates.
  gl_Position = u_t_modelViewProjection * a_position;

  // Image quads require that the texture to be inverted
  v_texcoord = a_uv0 * u_uvCoordOffsetScale.zw + u_uvCoordOffsetScale.xy;

  // Apply a sinusoidal swaying animation to the texture 
  float offset = sin((u_time * u_swaySpeed) - u_swayPhase) * (u_swayDepth / 50.0);
  v_texcoord.x += offset * (1.0 - v_texcoord.y);
}
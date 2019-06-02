// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface WGLExt
{
    public static final int WGL_LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int WGL_NV_DX_interop = 1;
    public static final int BI_RLE8 = 1;
    public static final int BI_RLE4 = 2;
    public static final int WGL_SAMPLE_BUFFERS_EXT = 8257;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_Z_ARB = 8322;
    public static final int WGL_EXT_make_current_read = 1;
    public static final int WGL_ARB_pixel_format_float = 1;
    public static final int ERROR_INVALID_WINDOW_HANDLE = 1400;
    public static final int WGL_IMAGE_BUFFER_LOCK_I3D = 2;
    public static final int WGL_NEED_PALETTE_ARB = 8196;
    public static final int OBJ_BRUSH = 2;
    public static final int WGL_VIDEO_OUT_COLOR_AND_ALPHA_NV = 8390;
    public static final int OBJ_BITMAP = 7;
    public static final int WGL_GENLOCK_SOURCE_EDGE_RISING_I3D = 8267;
    public static final int SW_SHOWMINNOACTIVE = 7;
    public static final int WGL_STEREO_EMITTER_ENABLE_3DL = 8277;
    public static final int SW_MINIMIZE = 6;
    public static final int SW_RESTORE = 9;
    public static final int WGL_ARB_buffer_region = 1;
    public static final int WGL_EXT_pbuffer = 1;
    public static final int OBJ_EXTPEN = 11;
    public static final int WGL_NUMBER_OVERLAYS_EXT = 8200;
    public static final int WGL_AUX2_ARB = 8329;
    public static final int WGL_STENCIL_BITS_ARB = 8227;
    public static final int WGL_UNIQUE_ID_NV = 8398;
    public static final int WGL_TRANSPARENT_VALUE_EXT = 8203;
    public static final int PFD_DRAW_TO_WINDOW = 4;
    public static final int WGL_NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int BI_RGB = 0;
    public static final int WGL_SWAP_COPY_ARB = 8233;
    public static final int WGL_CONTEXT_LAYER_PLANE_ARB = 8339;
    public static final int WGL_SUPPORT_GDI_ARB = 8207;
    public static final int WGL_NV_vertex_array_range = 1;
    public static final int WGL_AUX9_ARB = 8336;
    public static final int WGL_ARB_context_flush_control = 1;
    public static final int WGL_FULL_ACCELERATION_ARB = 8231;
    public static final int DIB_PAL_COLORS = 1;
    public static final int WGL_GREEN_BITS_ARB = 8215;
    public static final int WGL_GENLOCK_SOURCE_EXTERNAL_FIELD_I3D = 8262;
    public static final int WGL_MAX_PBUFFER_HEIGHT_EXT = 8240;
    public static final int WGL_TEXTURE_FLOAT_RGBA_NV = 8376;
    public static final int WGL_ACCUM_GREEN_BITS_EXT = 8223;
    public static final int WGL_NV_float_buffer = 1;
    public static final int WGL_3DL_stereo_control = 1;
    public static final int WGL_TEXTURE_1D_ARB = 8313;
    public static final int WGL_ACCUM_GREEN_BITS_ARB = 8223;
    public static final int WGL_BIND_TO_VIDEO_RGBA_NV = 8385;
    public static final int WGL_EXT_extensions_string = 1;
    public static final int WGL_AUX3_ARB = 8330;
    public static final int PFD_MAIN_PLANE = 0;
    public static final int WGL_BIND_TO_VIDEO_RGB_NV = 8384;
    public static final int WGL_AUX_BUFFERS_ARB = 8228;
    public static final int WGL_COLOR_SAMPLES_NV = 8377;
    public static final int WGL_EXT_framebuffer_sRGB = 1;
    public static final int WGL_NV_present_video = 1;
    public static final int WGL_CONTEXT_RELEASE_BEHAVIOR_NONE_ARB = 0;
    public static final int WGL_NUMBER_PIXEL_FORMATS_ARB = 8192;
    public static final int WGL_GREEN_BITS_EXT = 8215;
    public static final int PFD_TYPE_COLORINDEX = 1;
    public static final int WGL_COVERAGE_SAMPLES_NV = 8258;
    public static final int GLEXT_64_TYPES_DEFINED = 1;
    public static final int WGL_OML_sync_control = 1;
    public static final int WGL_I3D_genlock = 1;
    public static final int WGL_3DFX_multisample = 1;
    public static final int WGL_GPU_NUM_SIMD_AMD = 8614;
    public static final int WGL_CONTEXT_DEBUG_BIT_ARB = 1;
    public static final int WGL_NV_DX_interop2 = 1;
    public static final int WGL_CONTEXT_FLAGS_ARB = 8340;
    public static final int WGL_AUX8_ARB = 8335;
    public static final int WGL_SAMPLE_BUFFERS_ARB = 8257;
    public static final int WGL_OPTIMAL_PBUFFER_HEIGHT_EXT = 8242;
    public static final int WGL_RED_SHIFT_ARB = 8214;
    public static final int WGL_DEPTH_TEXTURE_FORMAT_NV = 8357;
    public static final int WGL_NUMBER_PIXEL_FORMATS_EXT = 8192;
    public static final int WGL_I3D_gamma = 1;
    public static final int WGL_I3D_swap_frame_lock = 1;
    public static final int WGL_NV_copy_image = 1;
    public static final int PFD_OVERLAY_PLANE = 1;
    public static final int WGL_NEED_PALETTE_EXT = 8196;
    public static final int PFD_STEREO = 2;
    public static final int WGL_DIGITAL_VIDEO_GAMMA_CORRECTED_I3D = 8275;
    public static final int PFD_GENERIC_ACCELERATED = 4096;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_FLOAT_RGBA_NV = 8372;
    public static final int ERROR_INCOMPATIBLE_DEVICE_CONTEXTS_ARB = 8276;
    public static final int WGL_PBUFFER_HEIGHT_EXT = 8245;
    public static final int WGL_WGLEXT_VERSION = 20150623;
    public static final int WGL_ATI_pixel_format_float = 1;
    public static final int ERROR_NO_SYSTEM_RESOURCES = 1450;
    public static final int WGL_ARB_framebuffer_sRGB = 1;
    public static final int WGL_AMD_gpu_association = 1;
    public static final int WGL_TRANSPARENT_RED_VALUE_ARB = 8247;
    public static final int WGL_AUX4_ARB = 8331;
    public static final int WGL_SAMPLE_BUFFERS_3DFX = 8288;
    public static final int WGL_VIDEO_OUT_STACKED_FIELDS_1_2 = 8395;
    public static final int WGL_RED_SHIFT_EXT = 8214;
    public static final int WGL_ARB_multisample = 1;
    public static final int WGL_TEXTURE_FLOAT_RG_NV = 8374;
    public static final int WGL_PIXEL_TYPE_EXT = 8211;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_DEPTH_NV = 8356;
    public static final int WGL_MAX_PBUFFER_HEIGHT_ARB = 8240;
    public static final int __wglext_h_ = 1;
    public static final int WGL_MAX_PBUFFER_PIXELS_ARB = 8238;
    public static final int ERROR_MISSING_AFFINITY_MASK_NV = 8401;
    public static final int WGL_SWAP_EXCHANGE_EXT = 8232;
    public static final int WGL_PBUFFER_HEIGHT_ARB = 8245;
    public static final int WGL_GPU_CLOCK_AMD = 8612;
    public static final int WGL_CONTEXT_ES2_PROFILE_BIT_EXT = 4;
    public static final int SW_SHOWNA = 8;
    public static final int WGL_NV_render_texture_rectangle = 1;
    public static final int WGL_NO_TEXTURE_ARB = 8311;
    public static final int WGL_EXT_multisample = 1;
    public static final int WGL_VIDEO_OUT_STACKED_FIELDS_2_1 = 8396;
    public static final int WGL_FULL_ACCELERATION_EXT = 8231;
    public static final int WGL_SWAP_EXCHANGE_ARB = 8232;
    public static final int WGL_I3D_digital_video_control = 1;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_FLOAT_RGB_NV = 8371;
    public static final int WGL_NEED_SYSTEM_PALETTE_EXT = 8197;
    public static final int OBJ_METADC = 4;
    public static final int WGL_DEPTH_COMPONENT_NV = 8359;
    public static final int WGL_VIDEO_OUT_ALPHA_NV = 8388;
    public static final int WGL_SWAP_LAYER_BUFFERS_EXT = 8198;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_FLOAT_RG_NV = 8370;
    public static final int WGL_MAX_PBUFFER_PIXELS_EXT = 8238;
    public static final int WGL_STEREO_EXT = 8210;
    public static final int WGL_MIPMAP_TEXTURE_ARB = 8308;
    public static final int WGL_TEXTURE_RECTANGLE_NV = 8354;
    public static final int WGL_SAMPLES_3DFX = 8289;
    public static final int SW_NORMAL = 1;
    public static final int WGL_DRAW_TO_PBUFFER_EXT = 8237;
    public static final int WGL_SUPPORT_GDI_EXT = 8207;
    public static final int WGL_ARB_render_texture = 1;
    public static final int WGL_STENCIL_BITS_EXT = 8227;
    public static final int WGL_TEXTURE_CUBE_MAP_ARB = 8312;
    public static final int WGL_DIGITAL_VIDEO_CURSOR_ALPHA_VALUE_I3D = 8273;
    public static final int WGL_GENLOCK_SOURCE_DIGITAL_FIELD_I3D = 8265;
    public static final long PFD_STEREO_DONTCARE = 2147483648L;
    public static final int WGL_AUX5_ARB = 8332;
    public static final int OBJ_COLORSPACE = 14;
    public static final int WGL_MAX_PBUFFER_WIDTH_EXT = 8239;
    public static final int OBJ_MEMDC = 10;
    public static final int WGL_TEXTURE_FORMAT_ARB = 8306;
    public static final int WGL_GPU_OPENGL_VERSION_STRING_AMD = 7938;
    public static final int WGL_EXT_create_context_es2_profile = 1;
    public static final int WGL_VIDEO_OUT_COLOR_NV = 8387;
    public static final int WGL_GPU_VENDOR_AMD = 7936;
    public static final int PFD_SWAP_EXCHANGE = 512;
    public static final int PFD_DRAW_TO_BITMAP = 8;
    public static final int WGL_NEED_SYSTEM_PALETTE_ARB = 8197;
    public static final int WGL_NV_gpu_affinity = 1;
    public static final int WGL_ARB_make_current_read = 1;
    public static final int WGL_GAMMA_EXCLUDE_DESKTOP_I3D = 8271;
    public static final int WGL_GREEN_SHIFT_EXT = 8216;
    public static final int WGL_SWAP_LAYER_BUFFERS_ARB = 8198;
    public static final int PFD_DOUBLEBUFFER = 1;
    public static final int WGL_CONTEXT_ES_PROFILE_BIT_EXT = 4;
    public static final int WGL_GENLOCK_SOURCE_EXTERNAL_SYNC_I3D = 8261;
    public static final int WGL_IMAGE_BUFFER_MIN_ACCESS_I3D = 1;
    public static final int OBJ_ENHMETADC = 12;
    public static final int WGL_CONTEXT_MAJOR_VERSION_ARB = 8337;
    public static final int WGL_GREEN_SHIFT_ARB = 8216;
    public static final int WGL_MAX_PBUFFER_WIDTH_ARB = 8239;
    public static final int WGL_DRAW_TO_PBUFFER_ARB = 8237;
    public static final int WGL_PIXEL_TYPE_ARB = 8211;
    public static final int WGL_NV_video_output = 1;
    public static final int PFD_GENERIC_FORMAT = 64;
    public static final int WGL_CONTEXT_MINOR_VERSION_ARB = 8338;
    public static final int SW_SHOWMAXIMIZED = 3;
    public static final int WGL_COLOR_BITS_ARB = 8212;
    public static final int WGL_BIND_TO_TEXTURE_DEPTH_NV = 8355;
    public static final int WGL_GENLOCK_SOURCE_DIGITAL_SYNC_I3D = 8264;
    public static final int WGL_TRANSPARENT_GREEN_VALUE_ARB = 8248;
    public static final int WGL_I3D_swap_frame_usage = 1;
    public static final int ERROR_INVALID_PIXEL_TYPE_ARB = 8259;
    public static final int WGL_TEXTURE_FLOAT_R_NV = 8373;
    public static final int WGL_ALPHA_BITS_EXT = 8219;
    public static final int WGL_AUX6_ARB = 8333;
    public static final int WGL_VIDEO_OUT_FIELD_2 = 8394;
    public static final int WGL_COLOR_BITS_EXT = 8212;
    public static final int WGL_VIDEO_OUT_FIELD_1 = 8393;
    public static final int ERROR_INVALID_DATA = 13;
    public static final int WGL_PBUFFER_LARGEST_EXT = 8243;
    public static final int WGL_SAMPLES_EXT = 8258;
    public static final int PFD_UNDERLAY_PLANE = -1;
    public static final int WGL_DIGITAL_VIDEO_CURSOR_INCLUDED_I3D = 8274;
    public static final int WGL_ARB_pbuffer = 1;
    public static final int WGL_FLOAT_COMPONENTS_NV = 8368;
    public static final int WGL_TRANSPARENT_BLUE_VALUE_ARB = 8249;
    public static final int WGL_STEREO_POLARITY_INVERT_3DL = 8280;
    public static final int WGL_PBUFFER_LARGEST_ARB = 8243;
    public static final int WGL_NO_ACCELERATION_ARB = 8229;
    public static final int PFD_SUPPORT_GDI = 16;
    public static final int WGL_TEXTURE_FLOAT_RGB_NV = 8375;
    public static final int WGL_ALPHA_BITS_ARB = 8219;
    public static final int WGL_ARB_robustness_application_isolation = 1;
    public static final int WGL_BIND_TO_TEXTURE_RGB_ARB = 8304;
    public static final int WGL_DIGITAL_VIDEO_CURSOR_ALPHA_FRAMEBUFFER_I3D = 8272;
    public static final int WGL_TYPE_RGBA_EXT = 8235;
    public static final int WGL_GPU_RAM_AMD = 8611;
    public static final int PFD_NEED_SYSTEM_PALETTE = 256;
    public static final int SW_MAXIMIZE = 3;
    public static final int WGL_STEREO_ARB = 8210;
    public static final int WGL_NV_delay_before_swap = 1;
    public static final int WGL_TYPE_COLORINDEX_EXT = 8236;
    public static final int SW_HIDE = 0;
    public static final int WGL_MIPMAP_LEVEL_ARB = 8315;
    public static final int WGL_STENCIL_BUFFER_BIT_ARB = 8;
    public static final int OBJ_METAFILE = 9;
    public static final int WGL_ARB_extensions_string = 1;
    public static final int WGL_SWAP_UNDEFINED_ARB = 8234;
    public static final int WGL_ACCUM_ALPHA_BITS_EXT = 8225;
    public static final int WGL_TRANSPARENT_ALPHA_VALUE_ARB = 8250;
    public static final int WGL_NV_video_capture = 1;
    public static final int SW_SHOWMINIMIZED = 2;
    public static final int WGL_DRAW_TO_WINDOW_ARB = 8193;
    public static final int PFD_DOUBLEBUFFER_DONTCARE = 1073741824;
    public static final int WGL_CONTEXT_ROBUST_ACCESS_BIT_ARB = 4;
    public static final int SW_MAX = 11;
    public static final int WGL_CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;
    public static final int OBJ_DC = 3;
    public static final int WGL_NO_ACCELERATION_EXT = 8229;
    public static final int WGL_BACK_RIGHT_ARB = 8326;
    public static final int WGL_ARB_robustness_share_group_isolation = 1;
    public static final int OBJ_PEN = 1;
    public static final int WGL_FRONT_RIGHT_ARB = 8324;
    public static final int WGL_I3D_image_buffer = 1;
    public static final int WGL_ACCUM_RED_BITS_EXT = 8222;
    public static final int WGL_BACK_COLOR_BUFFER_BIT_ARB = 2;
    public static final int OBJ_ENHMETAFILE = 13;
    public static final int SW_FORCEMINIMIZE = 11;
    public static final int ERROR_SUCCESS = 0;
    public static final int WGL_SWAP_UNDEFINED_EXT = 8234;
    public static final int WGL_ACCESS_READ_WRITE_NV = 1;
    public static final int WGL_AUX7_ARB = 8334;
    public static final int WGL_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
    public static final int WGL_TYPE_COLORINDEX_ARB = 8236;
    public static final int WGL_GENERIC_ACCELERATION_ARB = 8230;
    public static final int WGL_BLUE_SHIFT_ARB = 8218;
    public static final int ERROR_INCOMPATIBLE_AFFINITY_MASKS_NV = 8400;
    public static final int ERROR_INVALID_VERSION_ARB = 8341;
    public static final int WGL_SHARE_DEPTH_ARB = 8204;
    public static final int WGL_TYPE_RGBA_FLOAT_ATI = 8608;
    public static final int WGL_CONTEXT_CORE_PROFILE_BIT_ARB = 1;
    public static final int WGL_ACCUM_ALPHA_BITS_ARB = 8225;
    public static final int WGL_DOUBLE_BUFFER_EXT = 8209;
    public static final int WGL_VIDEO_OUT_COLOR_AND_DEPTH_NV = 8391;
    public static final int WGL_GENLOCK_SOURCE_EXTERNAL_TTL_I3D = 8263;
    public static final int WGL_DRAW_TO_WINDOW_EXT = 8193;
    public static final int WGL_EXT_pixel_format_packed_float = 1;
    public static final int WGL_FRONT_COLOR_BUFFER_BIT_ARB = 1;
    public static final int SW_SHOWNORMAL = 1;
    public static final int ERROR_INVALID_PROFILE_ARB = 8342;
    public static final int WGL_EXT_pixel_format = 1;
    public static final int WGL_ACCUM_RED_BITS_ARB = 8222;
    public static final int ERROR_PROC_NOT_FOUND = 127;
    public static final int WGL_ARB_create_context = 1;
    public static final int WGL_CONTEXT_PROFILE_MASK_ARB = 37158;
    public static final int WGL_STEREO_EMITTER_DISABLE_3DL = 8278;
    public static final int PFD_SWAP_LAYER_BUFFERS = 2048;
    public static final int WGL_DOUBLE_BUFFER_ARB = 8209;
    public static final int WGL_PBUFFER_WIDTH_EXT = 8244;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_Z_ARB = 8321;
    public static final int WGL_TEXTURE_2D_ARB = 8314;
    public static final int WGL_SAMPLES_ARB = 8258;
    public static final int WGL_BIND_TO_TEXTURE_RGBA_ARB = 8305;
    public static final int WGL_DEPTH_BUFFER_BIT_ARB = 4;
    public static final int WGL_TRANSPARENT_INDEX_VALUE_ARB = 8251;
    public static final int WGL_GENERIC_ACCELERATION_EXT = 8230;
    public static final int WGL_BLUE_SHIFT_EXT = 8218;
    public static final int WGL_NV_render_depth_texture = 1;
    public static final int WGL_TYPE_RGBA_ARB = 8235;
    public static final int WGL_SHARE_DEPTH_EXT = 8204;
    public static final int WGL_ACCESS_READ_ONLY_NV = 0;
    public static final int OBJ_PAL = 5;
    public static final int WGL_GENLOCK_SOURCE_EDGE_BOTH_I3D = 8268;
    public static final int WGL_NUM_VIDEO_CAPTURE_SLOTS_NV = 8399;
    public static final int WGL_GPU_NUM_RB_AMD = 8615;
    public static final int WGL_DEPTH_FLOAT_EXT = 8256;
    public static final int WGL_NUMBER_UNDERLAYS_EXT = 8201;
    public static final int WGL_BLUE_BITS_EXT = 8217;
    public static final int WGL_TRANSPARENT_ARB = 8202;
    public static final int WGL_ACCUM_BLUE_BITS_ARB = 8224;
    public static final int WGL_GENLOCK_SOURCE_MULTIVIEW_I3D = 8260;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_Y_ARB = 8319;
    public static final int WGL_SUPPORT_OPENGL_ARB = 8208;
    public static final int WGL_RED_BITS_EXT = 8213;
    public static final int WGL_CUBE_MAP_FACE_ARB = 8316;
    public static final int WGL_EXT_depth_float = 1;
    public static final int PFD_DEPTH_DONTCARE = 536870912;
    public static final int WGL_PBUFFER_WIDTH_ARB = 8244;
    public static final int WGL_VIDEO_OUT_DEPTH_NV = 8389;
    public static final int WGL_BLUE_BITS_ARB = 8217;
    public static final int WGL_TEXTURE_DEPTH_COMPONENT_NV = 8358;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_X_ARB = 8318;
    public static final int WGL_CONTEXT_RELEASE_BEHAVIOR_FLUSH_ARB = 8344;
    public static final int WGL_ACCUM_BLUE_BITS_EXT = 8224;
    public static final int WGL_GAMMA_TABLE_SIZE_I3D = 8270;
    public static final int PFD_SWAP_COPY = 1024;
    public static final int WGL_ALPHA_SHIFT_EXT = 8220;
    public static final int WGL_EXT_create_context_es_profile = 1;
    public static final int PFD_SUPPORT_OPENGL = 32;
    public static final int WGL_ALPHA_SHIFT_ARB = 8220;
    public static final int WGL_NUMBER_UNDERLAYS_ARB = 8201;
    public static final int WGL_SUPPORT_OPENGL_EXT = 8208;
    public static final int WGL_TEXTURE_RGBA_ARB = 8310;
    public static final int WGL_TRANSPARENT_EXT = 8202;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_FLOAT_R_NV = 8369;
    public static final int WGL_CONTEXT_RESET_ISOLATION_BIT_ARB = 8;
    public static final int WGL_ACCESS_WRITE_DISCARD_NV = 2;
    public static final int WGL_AUX_BUFFERS_EXT = 8228;
    public static final int SW_SHOWNOACTIVATE = 4;
    public static final int WGL_PBUFFER_LOST_ARB = 8246;
    public static final int WGL_TYPE_RGBA_FLOAT_ARB = 8608;
    public static final int WGL_NV_multisample_coverage = 1;
    public static final int WGL_SHARE_STENCIL_EXT = 8205;
    public static final int WGL_GPU_RENDERER_STRING_AMD = 7937;
    public static final int WGL_RED_BITS_ARB = 8213;
    public static final int WGL_EXT_swap_control = 1;
    public static final int WGL_AUX0_ARB = 8327;
    public static final int PFD_TYPE_RGBA = 0;
    public static final int SW_SHOW = 5;
    public static final int SW_SHOWDEFAULT = 10;
    public static final int WGL_NUMBER_OVERLAYS_ARB = 8200;
    public static final int WGL_STEREO_POLARITY_NORMAL_3DL = 8279;
    public static final int WGL_GPU_NUM_SPI_AMD = 8616;
    public static final int PFD_SUPPORT_DIRECTDRAW = 8192;
    public static final int WGL_ACCUM_BITS_EXT = 8221;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_Y_ARB = 8320;
    public static final int WGL_CONTEXT_COMPATIBILITY_PROFILE_BIT_ARB = 2;
    public static final int WGL_DEPTH_BITS_EXT = 8226;
    public static final int WGL_ARB_create_context_robustness = 1;
    public static final int WGL_SWAP_COPY_EXT = 8233;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_X_ARB = 8317;
    public static final int WGL_TEXTURE_TARGET_ARB = 8307;
    public static final int WGL_BACK_LEFT_ARB = 8325;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_RGBA_NV = 8353;
    public static final int DIB_RGB_COLORS = 0;
    public static final int WGL_ACCELERATION_EXT = 8195;
    public static final int WGL_TEXTURE_RGB_ARB = 8309;
    public static final int WGL_EXT_swap_control_tear = 1;
    public static final int BI_BITFIELDS = 3;
    public static final int WGL_SHARE_STENCIL_ARB = 8205;
    public static final int WGL_OPTIMAL_PBUFFER_WIDTH_EXT = 8241;
    public static final int WGL_DRAW_TO_BITMAP_ARB = 8194;
    public static final int WGL_VIDEO_OUT_FRAME = 8392;
    public static final int HAS_STDDEF = 1;
    public static final int WGL_EXT_display_color_table = 1;
    public static final int WGL_FRAMEBUFFER_SRGB_CAPABLE_ARB = 8361;
    public static final int WGL_DEPTH_BITS_ARB = 8226;
    public static final int WGL_SHARE_ACCUM_EXT = 8206;
    public static final int WGL_FRONT_LEFT_ARB = 8323;
    public static final int WGL_ARB_create_context_profile = 1;
    public static final int WGL_AUX1_ARB = 8328;
    public static final int WGL_GPU_NUM_PIPES_AMD = 8613;
    public static final int WGL_SWAP_METHOD_ARB = 8199;
    public static final int WGL_NV_swap_group = 1;
    public static final int OBJ_FONT = 6;
    public static final int WGL_NUM_VIDEO_SLOTS_NV = 8432;
    public static final int PFD_NEED_PALETTE = 128;
    public static final int ERROR_INVALID_PIXEL_TYPE_EXT = 8259;
    public static final int WGL_CONTEXT_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int WGL_ACCELERATION_ARB = 8195;
    public static final int WGL_CONTEXT_RELEASE_BEHAVIOR_ARB = 8343;
    public static final int WGL_ACCUM_BITS_ARB = 8221;
    public static final int WGL_GENLOCK_SOURCE_EDGE_FALLING_I3D = 8266;
    public static final int WGL_DRAW_TO_BITMAP_EXT = 8194;
    public static final int OBJ_REGION = 8;
    public static final int WGL_SHARE_ACCUM_ARB = 8206;
    public static final int WGL_FRAMEBUFFER_SRGB_CAPABLE_EXT = 8361;
    public static final int WGL_TYPE_RGBA_UNSIGNED_FLOAT_EXT = 8360;
    public static final int WGL_BIND_TO_VIDEO_RGB_AND_DEPTH_NV = 8386;
    public static final int WGL_SWAP_METHOD_EXT = 8199;
    public static final int WGL_ARB_pixel_format = 1;
    public static final int ERROR_INVALID_PIXEL_FORMAT = 2000;
    public static final int WGL_BIND_TO_TEXTURE_RECTANGLE_RGB_NV = 8352;
    
    long wglCreateBufferRegionARB(final long p0, final int p1, final int p2);
    
    void wglDeleteBufferRegionARB(final long p0);
    
    boolean wglSaveBufferRegionARB(final long p0, final int p1, final int p2, final int p3, final int p4);
    
    boolean wglRestoreBufferRegionARB(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    long wglCreateContextAttribsARB(final long p0, final long p1, final IntBuffer p2);
    
    String wglGetExtensionsStringARB(final long p0);
    
    boolean wglMakeContextCurrent(final long p0, final long p1, final long p2);
    
    long wglGetCurrentReadDC();
    
    long wglCreatePbufferARB(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    long wglGetPbufferDCARB(final long p0);
    
    int wglReleasePbufferDCARB(final long p0, final long p1);
    
    boolean wglDestroyPbufferARB(final long p0);
    
    boolean wglQueryPbufferARB(final long p0, final int p1, final IntBuffer p2);
    
    boolean wglGetPixelFormatAttribivARB(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4, final IntBuffer p5);
    
    boolean wglGetPixelFormatAttribfvARB(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4, final FloatBuffer p5);
    
    boolean wglChoosePixelFormatARB(final long p0, final IntBuffer p1, final FloatBuffer p2, final int p3, final IntBuffer p4, final IntBuffer p5);
    
    boolean wglBindTexImageARB(final long p0, final int p1);
    
    boolean wglReleaseTexImageARB(final long p0, final int p1);
    
    boolean wglSetPbufferAttribARB(final long p0, final IntBuffer p1);
    
    boolean wglSetStereoEmitterState3DL(final long p0, final int p1);
    
    int wglGetGPUIDsAMD(final int p0, final IntBuffer p1);
    
    int wglGetGPUInfoAMD(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    int wglGetContextGPUIDAMD(final long p0);
    
    long wglCreateAssociatedContextAMD(final int p0);
    
    long wglCreateAssociatedContextAttribsAMD(final int p0, final long p1, final IntBuffer p2);
    
    boolean wglDeleteAssociatedContextAMD(final long p0);
    
    boolean wglMakeAssociatedContextCurrentAMD(final long p0);
    
    long wglGetCurrentAssociatedContextAMD();
    
    void wglBlitContextFramebufferAMD(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    boolean wglCreateDisplayColorTableEXT(final short p0);
    
    boolean wglLoadDisplayColorTableEXT(final ShortBuffer p0, final int p1);
    
    boolean wglBindDisplayColorTableEXT(final short p0);
    
    void wglDestroyDisplayColorTableEXT(final short p0);
    
    String wglGetExtensionsStringEXT();
    
    long wglCreatePbufferEXT(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    long wglGetPbufferDCEXT(final long p0);
    
    int wglReleasePbufferDCEXT(final long p0, final long p1);
    
    boolean wglDestroyPbufferEXT(final long p0);
    
    boolean wglQueryPbufferEXT(final long p0, final int p1, final IntBuffer p2);
    
    boolean wglGetPixelFormatAttribivEXT(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4, final IntBuffer p5);
    
    boolean wglGetPixelFormatAttribfvEXT(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4, final FloatBuffer p5);
    
    boolean wglChoosePixelFormatEXT(final long p0, final IntBuffer p1, final FloatBuffer p2, final int p3, final IntBuffer p4, final IntBuffer p5);
    
    boolean wglSwapIntervalEXT(final int p0);
    
    int wglGetSwapIntervalEXT();
    
    boolean wglEnableFrameLockI3D();
    
    boolean wglDisableFrameLockI3D();
    
    boolean wglIsEnabledFrameLockI3D(final ByteBuffer p0);
    
    boolean wglQueryFrameLockMasterI3D(final ByteBuffer p0);
    
    boolean wglGetFrameUsageI3D(final FloatBuffer p0);
    
    boolean wglBeginFrameTrackingI3D();
    
    boolean wglEndFrameTrackingI3D();
    
    boolean wglQueryFrameTrackingI3D(final IntBuffer p0, final IntBuffer p1, final FloatBuffer p2);
    
    boolean wglDXSetResourceShareHandleNV(final Buffer p0, final long p1);
    
    long wglDXOpenDeviceNV(final Buffer p0);
    
    boolean wglDXCloseDeviceNV(final long p0);
    
    long wglDXRegisterObjectNV(final long p0, final Buffer p1, final int p2, final int p3, final int p4);
    
    boolean wglDXUnregisterObjectNV(final long p0, final long p1);
    
    boolean wglDXObjectAccessNV(final long p0, final int p1);
    
    boolean wglDXLockObjectsNV(final long p0, final int p1, final PointerBuffer p2);
    
    boolean wglDXUnlockObjectsNV(final long p0, final int p1, final PointerBuffer p2);
    
    boolean wglCopyImageSubDataNV(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final int p15, final int p16);
    
    boolean wglDelayBeforeSwapNV(final long p0, final float p1);
    
    int wglEnumerateVideoDevicesNV(final long p0, final PointerBuffer p1);
    
    boolean wglBindVideoDeviceNV(final long p0, final int p1, final long p2, final IntBuffer p3);
    
    boolean wglQueryCurrentContextNV(final int p0, final IntBuffer p1);
    
    boolean wglJoinSwapGroupNV(final long p0, final int p1);
    
    boolean wglBindSwapBarrierNV(final int p0, final int p1);
    
    boolean wglQuerySwapGroupNV(final long p0, final IntBuffer p1, final IntBuffer p2);
    
    boolean wglQueryMaxSwapGroupsNV(final long p0, final IntBuffer p1, final IntBuffer p2);
    
    boolean wglQueryFrameCountNV(final long p0, final IntBuffer p1);
    
    boolean wglResetFrameCountNV(final long p0);
    
    ByteBuffer wglAllocateMemoryNV(final int p0, final float p1, final float p2, final float p3);
    
    void wglFreeMemoryNV(final Buffer p0);
    
    boolean wglBindVideoCaptureDeviceNV(final int p0, final long p1);
    
    int wglEnumerateVideoCaptureDevicesNV(final long p0, final PointerBuffer p1);
    
    boolean wglLockVideoCaptureDeviceNV(final long p0, final long p1);
    
    boolean wglQueryVideoCaptureDeviceNV(final long p0, final long p1, final int p2, final IntBuffer p3);
    
    boolean wglReleaseVideoCaptureDeviceNV(final long p0, final long p1);
    
    boolean wglGetVideoDeviceNV(final long p0, final int p1, final PointerBuffer p2);
    
    boolean wglReleaseVideoDeviceNV(final long p0);
    
    boolean wglBindVideoImageNV(final long p0, final long p1, final int p2);
    
    boolean wglReleaseVideoImageNV(final long p0, final int p1);
    
    boolean wglSendPbufferToVideoNV(final long p0, final int p1, final LongBuffer p2, final boolean p3);
    
    boolean wglGetVideoInfoNV(final long p0, final LongBuffer p1, final LongBuffer p2);
    
    boolean isFunctionAvailable(final String p0);
    
    boolean isExtensionAvailable(final String p0);
}

// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface GL2ES3 extends GL2ES2
{
    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
    public static final int GL_MAX_IMAGE_UNITS = 36664;
    public static final int GL_TEXTURE_MIN_LOD = 33082;
    public static final int GL_FLOAT_MAT3x4 = 35688;
    public static final int GL_FLOAT_MAT3x2 = 35687;
    public static final int GL_FRAMEBUFFER_BARRIER_BIT = 1024;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5_KHR = 37842;
    public static final int GL_UNIFORM_SIZE = 35384;
    public static final int GL_IMAGE_BINDING_ACCESS = 36670;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
    public static final int GL_MAX_COMBINED_IMAGE_UNIFORMS = 37071;
    public static final int GL_IMAGE_BINDING_LAYER = 36669;
    public static final int GL_UNIFORM_BLOCK_DATA_SIZE = 35392;
    public static final int GL_FRAMEBUFFER_UNDEFINED = 33305;
    public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37494;
    public static final long GL_ALL_BARRIER_BITS = 4294967295L;
    public static final int GL_TEXTURE_FETCH_BARRIER_BIT = 8;
    public static final int GL_IMAGE_2D_ARRAY = 36947;
    public static final int GL_TEXTURE_GREEN_TYPE = 35857;
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = 37065;
    public static final int GL_COMMAND_BARRIER_BIT = 64;
    public static final int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    public static final int GL_TEXTURE_BLUE_SIZE = 32862;
    public static final int GL_ACTIVE_UNIFORM_BLOCKS = 35382;
    public static final int GL_RGB32I = 36227;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x5_KHR = 37813;
    public static final int GL_TEXTURE_SWIZZLE_R = 36418;
    public static final int GL_TEXTURE_SWIZZLE_B = 36420;
    public static final int GL_TEXTURE_SWIZZLE_A = 36421;
    public static final int GL_TEXTURE_SWIZZLE_G = 36419;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 35053;
    public static final int GL_R32UI = 33334;
    public static final int GL_COMPRESSED_RG11_EAC = 37490;
    public static final int GL_IMAGE_BINDING_NAME = 36666;
    public static final int GL_TRANSFORM_FEEDBACK_PAUSED = 36387;
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT = 4;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6_KHR = 37844;
    public static final int GL_RGBA_INTEGER = 36249;
    public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
    public static final int GL_PACK_ROW_LENGTH = 3330;
    public static final int GL_QUERY_WAIT = 36371;
    public static final int GL_DYNAMIC_COPY = 35050;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
    public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE = 37761;
    public static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 35380;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x10_KHR = 37820;
    public static final int GL_UNIFORM_NAME_LENGTH = 35385;
    public static final int GL_UNSIGNED_INT_IMAGE_2D = 36963;
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static final int GL_RED_INTEGER = 36244;
    public static final int GL_TEXTURE_STENCIL_SIZE = 35057;
    public static final int GL_TEXTURE_BINDING_BUFFER = 35884;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
    public static final int GL_UNSIGNED_INT_IMAGE_3D = 36964;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4_KHR = 37840;
    public static final int GL_COMPRESSED_SIGNED_RG11_EAC = 37491;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 35055;
    public static final int GL_INT_IMAGE_3D = 36953;
    public static final int GL_INT_IMAGE_2D = 36952;
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 35394;
    public static final int GL_INT_SAMPLER_2D_ARRAY = 36303;
    public static final int GL_SAMPLER_2D_ARRAY = 36289;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    public static final int GL_FLOAT_MAT4x3 = 35690;
    public static final int GL_FLOAT_MAT4x2 = 35689;
    public static final int GL_R16UI = 33332;
    public static final int GL_TEXTURE_RED_TYPE = 35856;
    public static final int GL_COPY_WRITE_BUFFER = 36663;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6_KHR = 37849;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x6_KHR = 37817;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_ARRAY = 36969;
    public static final int GL_IMAGE_CUBE_MAP_ARRAY = 36948;
    public static final int GL_TRIANGLES_ADJACENCY_EXT = 12;
    public static final int GL_ATOMIC_COUNTER_BARRIER_BIT = 4096;
    public static final int GL_R8I = 33329;
    public static final int GL_STREAM_COPY = 35042;
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 36878;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 37587;
    public static final int GL_UNPACK_SKIP_IMAGES = 32877;
    public static final int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 36879;
    public static final int GL_UNIFORM_MATRIX_STRIDE = 35389;
    public static final int GL_FIRST_VERTEX_CONVENTION_EXT = 36429;
    public static final int GL_TRANSFORM_FEEDBACK_BARRIER_BIT = 2048;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 35398;
    public static final int GL_STATIC_READ = 35045;
    public static final int GL_SIGNED_NORMALIZED = 36764;
    public static final int GL_TEXTURE_UPDATE_BARRIER_BIT = 256;
    public static final int GL_TRANSFORM_FEEDBACK_ACTIVE = 36388;
    public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 33367;
    public static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 35381;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x8_KHR = 37815;
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 37063;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_EXT = 35881;
    public static final int GL_UNIFORM_BUFFER_SIZE = 35370;
    public static final int GL_LAST_VERTEX_CONVENTION_EXT = 36430;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 37497;
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
    public static final int GL_COMPRESSED_RGB8_ETC2 = 37492;
    public static final int GL_ATOMIC_COUNTER_BUFFER_SIZE = 37571;
    public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY = 37762;
    public static final int GL_TEXTURE_BUFFER = 35882;
    public static final int GL_MINOR_VERSION = 33308;
    public static final int GL_QUERY_BY_REGION_WAIT = 36373;
    public static final int GL_UNIFORM_BARRIER_BIT = 4;
    public static final int GL_VERTEX_ARRAY_BINDING = 34229;
    public static final int GL_BUFFER_UPDATE_BARRIER_BIT = 512;
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = 37064;
    public static final int GL_PIXEL_PACK_BUFFER = 35051;
    public static final int GL_PIXEL_BUFFER_BARRIER_BIT = 128;
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 35395;
    public static final int GL_RGBA16UI = 36214;
    public static final int GL_RASTERIZER_DISCARD = 35977;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
    public static final int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
    public static final int GL_UNPACK_IMAGE_HEIGHT = 32878;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12_KHR = 37853;
    public static final int GL_INT_SAMPLER_3D = 36299;
    public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 32;
    public static final int GL_MAX_ELEMENTS_VERTICES = 33000;
    public static final int GL_INT_SAMPLER_2D = 36298;
    public static final int GL_R8UI = 33330;
    public static final int GL_TEXTURE_ALPHA_TYPE = 35859;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static final int GL_IMAGE_BINDING_FORMAT = 36974;
    public static final int GL_SAMPLER_BUFFER = 36290;
    public static final int GL_MIN_SAMPLE_SHADING_VALUE = 35895;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = 36970;
    public static final int GL_RGBA8_SNORM = 36759;
    public static final int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 35372;
    public static final int GL_TEXTURE_2D_ARRAY = 35866;
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static final int GL_MAX_ELEMENT_INDEX = 36203;
    public static final int GL_COLOR_ATTACHMENT17 = 36081;
    public static final int GL_COLOR_ATTACHMENT16 = 36080;
    public static final int GL_COLOR_ATTACHMENT26 = 36090;
    public static final int GL_COLOR_ATTACHMENT25 = 36089;
    public static final int GL_COLOR_ATTACHMENT28 = 36092;
    public static final int GL_COLOR_ATTACHMENT27 = 36091;
    public static final int GL_COLOR_ATTACHMENT22 = 36086;
    public static final int GL_COLOR_ATTACHMENT21 = 36085;
    public static final int GL_COLOR_ATTACHMENT24 = 36088;
    public static final int GL_COLOR_ATTACHMENT23 = 36087;
    public static final int GL_COLOR_ATTACHMENT20 = 36084;
    public static final int GL_COLOR_ATTACHMENT19 = 36083;
    public static final int GL_COLOR_ATTACHMENT18 = 36082;
    public static final int GL_TEXTURE_DEPTH_TYPE = 35862;
    public static final int GL_COLOR_ATTACHMENT31 = 36095;
    public static final int GL_COLOR_ATTACHMENT30 = 36094;
    public static final int GL_COLOR_ATTACHMENT29 = 36093;
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 4099;
    public static final int GL_COLOR = 6144;
    public static final int GL_RG8_SNORM = 36757;
    public static final int GL_GREEN = 6404;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
    public static final int GL_NUM_SAMPLE_COUNTS = 37760;
    public static final int GL_STATIC_COPY = 35046;
    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 37592;
    public static final int GL_BUFFER_ACCESS_FLAGS = 37151;
    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 37590;
    public static final int GL_RGB16UI = 36215;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6_KHR = 37846;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4_KHR = 37841;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 36874;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5_KHR = 37843;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x8_KHR = 37818;
    public static final int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 37580;
    public static final int GL_UNSIGNED_INT_VEC4 = 36296;
    public static final int GL_INT_IMAGE_CUBE = 36955;
    public static final int GL_UNSIGNED_INT_VEC3 = 36295;
    public static final int GL_UNSIGNED_INT_VEC2 = 36294;
    public static final int GL_COPY_READ_BUFFER = 36662;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE = 36966;
    public static final int GL_COMPRESSED_RGBA8_ETC2_EAC = 37496;
    public static final int GL_R8_SNORM = 36756;
    public static final int GL_MAX_VERTEX_ATOMIC_COUNTERS = 37586;
    public static final int GL_MAX_FRAGMENT_IMAGE_UNIFORMS = 37070;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 37583;
    public static final int GL_READ_ONLY = 35000;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x5_KHR = 37816;
    public static final int GL_UNIFORM_OFFSET = 35387;
    public static final int GL_INT_SAMPLER_BUFFER = 36304;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    public static final int GL_COPY_READ_BUFFER_BINDING = 36662;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x5_KHR = 37810;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER = 36312;
    public static final int GL_UNSIGNED_INT_ATOMIC_COUNTER = 37595;
    public static final int GL_TEXTURE_SHARED_SIZE = 35903;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 35658;
    public static final int GL_BLUE = 6405;
    public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37495;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    public static final int GL_UNIFORM_BLOCK_NAME_LENGTH = 35393;
    public static final int GL_BUFFER_MAP_LENGTH = 37152;
    public static final int GL_RGB32UI = 36209;
    public static final int GL_IMAGE_CUBE = 36944;
    public static final int GL_RG_INTEGER = 33320;
    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 35885;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = 35371;
    public static final int GL_SRGB8 = 35905;
    public static final int GL_PACK_SKIP_PIXELS = 3332;
    public static final int GL_RGB_INTEGER = 36248;
    public static final int GL_FRAMEBUFFER_DEFAULT = 33304;
    public static final int GL_R16I = 33331;
    public static final int GL_COMPRESSED_SRGB8_ETC2 = 37493;
    public static final int GL_GEOMETRY_SHADER_BIT = 4;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
    public static final int GL_TEXTURE_ALPHA_SIZE = 32863;
    public static final int GL_DYNAMIC_READ = 35049;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
    public static final int GL_UNIFORM_BUFFER_START = 35369;
    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY = 36959;
    public static final int GL_MAX_UNIFORM_BLOCK_SIZE = 35376;
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static final int GL_RGBA16I = 36232;
    public static final int GL_TEXTURE_MAX_LEVEL = 33085;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
    public static final int GL_MAX_COMBINED_ATOMIC_COUNTERS = 37591;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_EXT = 13;
    public static final int GL_LINE_STRIP_ADJACENCY_EXT = 11;
    public static final int GL_UNIFORM_BLOCK_BINDING = 35391;
    public static final int GL_TEXTURE_GREEN_SIZE = 32861;
    public static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 35379;
    public static final int GL_UNIFORM_TYPE = 35383;
    public static final int GL_TEXTURE_HEIGHT = 4097;
    public static final int GL_RGB9_E5 = 35901;
    public static final int GL_TRANSFORM_FEEDBACK = 36386;
    public static final int GL_TEXTURE_BLUE_TYPE = 35858;
    public static final int GL_SAMPLER_CUBE_SHADOW = 36293;
    public static final int GL_LINES_ADJACENCY_EXT = 10;
    public static final int GL_RGBA8UI = 36220;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = 1;
    public static final int GL_DEPTH_COMPONENT32F = 36012;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
    public static final int GL_R32I = 33333;
    public static final int GL_IMAGE_BINDING_LAYERED = 36668;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x4_KHR = 37808;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10_KHR = 37851;
    public static final int GL_RGB8I = 36239;
    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = 37067;
    public static final int GL_RG16I = 33337;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_EXT = 36263;
    public static final int GL_RGBA32UI = 36208;
    public static final int GL_RG32UI = 33340;
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 35657;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    public static final int GL_ELEMENT_ARRAY_BARRIER_BIT = 2;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5_KHR = 37845;
    public static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = 36201;
    public static final int GL_INTERLEAVED_ATTRIBS = 35980;
    public static final int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
    public static final int GL_TEXTURE_WIDTH = 4096;
    public static final int GL_SAMPLE_SHADING = 35894;
    public static final int GL_IMAGE_3D = 36942;
    public static final int GL_LAST_VERTEX_CONVENTION = 36430;
    public static final int GL_CONTEXT_FLAGS = 33310;
    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = 37068;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
    public static final int GL_TEXTURE_MAX_LOD = 33083;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY = 36876;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x12_KHR = 37821;
    public static final int GL_RGBA32I = 36226;
    public static final int GL_RG32I = 33339;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x6_KHR = 37812;
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static final int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 37585;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
    public static final int GL_PACK_SKIP_ROWS = 3331;
    public static final int GL_NUM_EXTENSIONS = 33309;
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8_KHR = 37850;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x4_KHR = 37809;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 37588;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    public static final int GL_QUADS = 7;
    public static final int GL_ATOMIC_COUNTER_BUFFER_START = 37570;
    public static final int GL_DEPTH = 6145;
    public static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 35373;
    public static final int GL_IMAGE_BUFFER = 36945;
    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER = 36967;
    public static final int GL_READ_WRITE = 35002;
    public static final int GL_TEXTURE_DEPTH = 32881;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 37582;
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static final int GL_MAX_ELEMENTS_INDICES = 33001;
    public static final int GL_IMAGE_2D = 36941;
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS = 37069;
    public static final int GL_UNIFORM_BUFFER = 35345;
    public static final int GL_TEXTURE_RED_SIZE = 32860;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x6_KHR = 37814;
    public static final int GL_RGB8UI = 36221;
    public static final int GL_UNIFORM_IS_ROW_MAJOR = 35390;
    public static final int GL_UNIFORM_BLOCK_INDEX = 35386;
    public static final int GL_TEXTURE_BASE_LEVEL = 33084;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 35396;
    public static final int GL_INT_IMAGE_2D_ARRAY = 36958;
    public static final int GL_RGB16I = 36233;
    public static final int GL_COMPRESSED_R11_EAC = 37488;
    public static final int GL_MAX_TEXTURE_LOD_BIAS = 34045;
    public static final int GL_R11F_G11F_B10F = 35898;
    public static final int GL_RGBA8I = 36238;
    public static final int GL_INT_IMAGE_BUFFER = 36956;
    public static final int GL_TEXTURE_COMPRESSED = 34465;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 37581;
    public static final int GL_QUERY_NO_WAIT = 36372;
    public static final int GL_COMPRESSED_SIGNED_R11_EAC = 37489;
    public static final int GL_MIN = 32775;
    public static final int GL_PRIMITIVE_BOUNDING_BOX = 37566;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8_KHR = 37847;
    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 35378;
    public static final int GL_STREAM_READ = 35041;
    public static final int GL_MAX_VERTEX_IMAGE_UNIFORMS = 37066;
    public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
    public static final int GL_MAX = 32776;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 36877;
    public static final int GL_UNIFORM_ARRAY_STRIDE = 35388;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_EXT = 36264;
    public static final int GL_SEPARATE_ATTRIBS = 35981;
    public static final int GL_TEXTURE_DEPTH_SIZE = 34890;
    public static final int GL_RG8UI = 33336;
    public static final int GL_MAJOR_VERSION = 33307;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5_KHR = 37848;
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE = 35883;
    public static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = 35375;
    public static final int GL_FLOAT_MAT2x3 = 35685;
    public static final int GL_FLOAT_MAT2x4 = 35686;
    public static final int GL_RG16UI = 33338;
    public static final int GL_MAX_VARYING_COMPONENTS = 35659;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x10_KHR = 37819;
    public static final int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
    public static final int GL_INT_SAMPLER_CUBE = 36300;
    public static final int GL_RGB8_SNORM = 36758;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x5_KHR = 37811;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 37589;
    public static final int GL_RG8I = 33335;
    public static final int GL_READ_BUFFER = 3074;
    public static final int GL_DEPTH_STENCIL_TEXTURE_MODE = 37098;
    public static final int GL_PRIMITIVES_GENERATED = 35975;
    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 37584;
    public static final int GL_UNIFORM_BUFFER_BINDING = 35368;
    public static final int GL_ATOMIC_COUNTER_BUFFER = 37568;
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;
    public static final int GL_DEPTH32F_STENCIL8 = 36013;
    public static final int GL_IMAGE_BINDING_LEVEL = 36667;
    public static final int GL_BUFFER_MAP_OFFSET = 37153;
    public static final int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 37593;
    public static final int GL_RGB10_A2UI = 36975;
    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 37596;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
    public static final int GL_COPY_WRITE_BUFFER_BINDING = 36663;
    public static final int GL_STENCIL = 6146;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10_KHR = 37852;
    public static final int GL_ATOMIC_COUNTER_BUFFER_BINDING = 37569;
    public static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 35377;
    public static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = 35374;
    public static final int GL_INVALID_INDEX = -1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    
    void glReadBuffer(final int p0);
    
    void glGetTexLevelParameterfv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetTexLevelParameterfv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetTexLevelParameteriv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTexLevelParameteriv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glDrawRangeElements(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glUniformMatrix2x3fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2x3fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3x2fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3x2fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix2x4fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2x4fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4x2fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4x2fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3x4fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3x4fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4x3fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4x3fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glColorMaski(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4);
    
    void glGetBooleani_v(final int p0, final int p1, final ByteBuffer p2);
    
    void glGetBooleani_v(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glGetIntegeri_v(final int p0, final int p1, final IntBuffer p2);
    
    void glGetIntegeri_v(final int p0, final int p1, final int[] p2, final int p3);
    
    void glEnablei(final int p0, final int p1);
    
    void glDisablei(final int p0, final int p1);
    
    boolean glIsEnabledi(final int p0, final int p1);
    
    void glBeginTransformFeedback(final int p0);
    
    void glEndTransformFeedback();
    
    void glBindBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    void glBindBufferBase(final int p0, final int p1, final int p2);
    
    void glTransformFeedbackVaryings(final int p0, final int p1, final String[] p2, final int p3);
    
    void glGetTransformFeedbackVarying(final int p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final ByteBuffer p6);
    
    void glGetTransformFeedbackVarying(final int p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6, final int[] p7, final int p8, final byte[] p9, final int p10);
    
    void glBeginConditionalRender(final int p0, final int p1);
    
    void glEndConditionalRender();
    
    void glVertexAttribIPointer(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glGetVertexAttribIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVertexAttribIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glVertexAttribI4i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexAttribI4ui(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexAttribI4iv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI4iv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI4uiv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI4uiv(final int p0, final int[] p1, final int p2);
    
    void glGetUniformuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetUniformuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    int glGetFragDataLocation(final int p0, final String p1);
    
    void glUniform1ui(final int p0, final int p1);
    
    void glUniform2ui(final int p0, final int p1, final int p2);
    
    void glUniform3ui(final int p0, final int p1, final int p2, final int p3);
    
    void glUniform4ui(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glUniform1uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform1uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform2uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform2uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform3uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform3uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform4uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform4uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glClearBufferiv(final int p0, final int p1, final IntBuffer p2);
    
    void glClearBufferiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glClearBufferuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glClearBufferuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glClearBufferfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glClearBufferfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glClearBufferfi(final int p0, final int p1, final float p2, final int p3);
    
    String glGetStringi(final int p0, final int p1);
    
    void glBlitFramebuffer(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9);
    
    void glFramebufferTextureLayer(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glBindVertexArray(final int p0);
    
    void glDeleteVertexArrays(final int p0, final IntBuffer p1);
    
    void glDeleteVertexArrays(final int p0, final int[] p1, final int p2);
    
    void glGenVertexArrays(final int p0, final IntBuffer p1);
    
    void glGenVertexArrays(final int p0, final int[] p1, final int p2);
    
    boolean glIsVertexArray(final int p0);
    
    void glDrawArraysInstanced(final int p0, final int p1, final int p2, final int p3);
    
    void glDrawElementsInstanced(final int p0, final int p1, final int p2, final long p3, final int p4);
    
    void glTexBuffer(final int p0, final int p1, final int p2);
    
    void glCopyBufferSubData(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glGetUniformIndices(final int p0, final int p1, final String[] p2, final IntBuffer p3);
    
    void glGetUniformIndices(final int p0, final int p1, final String[] p2, final int[] p3, final int p4);
    
    void glGetActiveUniformsiv(final int p0, final int p1, final IntBuffer p2, final int p3, final IntBuffer p4);
    
    void glGetActiveUniformsiv(final int p0, final int p1, final int[] p2, final int p3, final int p4, final int[] p5, final int p6);
    
    int glGetUniformBlockIndex(final int p0, final String p1);
    
    void glGetActiveUniformBlockiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetActiveUniformBlockiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetActiveUniformBlockName(final int p0, final int p1, final int p2, final IntBuffer p3, final ByteBuffer p4);
    
    void glGetActiveUniformBlockName(final int p0, final int p1, final int p2, final int[] p3, final int p4, final byte[] p5, final int p6);
    
    void glUniformBlockBinding(final int p0, final int p1, final int p2);
    
    void glVertexAttribDivisor(final int p0, final int p1);
    
    void glMinSampleShading(final float p0);
    
    void glBlendEquationi(final int p0, final int p1);
    
    void glBlendEquationSeparatei(final int p0, final int p1, final int p2);
    
    void glBlendFunci(final int p0, final int p1, final int p2);
    
    void glBlendFuncSeparatei(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glBindTransformFeedback(final int p0, final int p1);
    
    void glDeleteTransformFeedbacks(final int p0, final IntBuffer p1);
    
    void glDeleteTransformFeedbacks(final int p0, final int[] p1, final int p2);
    
    void glGenTransformFeedbacks(final int p0, final IntBuffer p1);
    
    void glGenTransformFeedbacks(final int p0, final int[] p1, final int p2);
    
    boolean glIsTransformFeedback(final int p0);
    
    void glPauseTransformFeedback();
    
    void glResumeTransformFeedback();
    
    void glGetInternalformativ(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glGetInternalformativ(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glBindImageTexture(final int p0, final int p1, final int p2, final boolean p3, final int p4, final int p5, final int p6);
    
    void glMemoryBarrier(final int p0);
    
    void glFramebufferParameteri(final int p0, final int p1, final int p2);
    
    void glGetFramebufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetFramebufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glInvalidateFramebuffer(final int p0, final int p1, final IntBuffer p2);
    
    void glInvalidateFramebuffer(final int p0, final int p1, final int[] p2, final int p3);
    
    void glInvalidateSubFramebuffer(final int p0, final int p1, final IntBuffer p2, final int p3, final int p4, final int p5, final int p6);
    
    void glInvalidateSubFramebuffer(final int p0, final int p1, final int[] p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glTexStorage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void glTexStorage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glGetnUniformuiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetnUniformuiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glPrimitiveBoundingBox(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    void glFramebufferTextureEXT(final int p0, final int p1, final int p2, final int p3);
    
    boolean isPBOPackBound();
    
    boolean isPBOUnpackBound();
}

/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ public final class Crypt
/*     */ {
/*   5 */   static final byte[] Substitution = { 7, 8, 0, 8, 6, 4, 14, 4, 5, 12, 1, 7, 11, 15, 10, 8, 15, 8, 12, 12, 9, 4, 1, 14, 4, 6, 2, 4, 0, 10, 11, 9, 2, 15, 11, 1, 13, 2, 1, 9, 5, 14, 7, 0, 0, 2, 6, 6, 0, 7, 3, 8, 2, 9, 3, 15, 7, 15, 12, 15, 6, 4, 10, 0, 2, 3, 10, 11, 13, 8, 3, 10, 1, 7, 12, 15, 1, 8, 9, 13, 9, 1, 9, 4, 14, 4, 12, 5, 5, 12, 8, 11, 2, 3, 9, 14, 7, 7, 6, 9, 14, 15, 12, 8, 13, 1, 10, 6, 14, 13, 0, 7, 7, 10, 0, 1, 15, 5, 4, 11, 7, 11, 14, 12, 9, 5, 13, 1, 11, 13, 1, 3, 5, 13, 14, 6, 3, 0, 11, 11, 15, 3, 6, 4, 9, 13, 10, 3, 1, 4, 9, 4, 8, 3, 11, 14, 5, 0, 5, 2, 12, 11, 13, 5, 13, 5, 13, 2, 13, 9, 10, 12, 10, 0, 11, 3, 5, 3, 6, 9, 5, 1, 14, 14, 0, 14, 8, 2, 13, 2, 2, 0, 4, 15, 8, 5, 9, 6, 8, 6, 11, 10, 11, 15, 0, 7, 2, 8, 12, 7, 3, 10, 1, 4, 2, 5, 15, 7, 10, 12, 14, 5, 9, 3, 14, 7, 1, 2, 14, 1, 15, 4, 10, 6, 12, 6, 15, 4, 3, 0, 12, 0, 3, 6, 15, 8, 7, 11, 2, 13, 12, 6, 10, 10, 8, 13 };
/*     */ 
/*  25 */   static final byte[] positionTable = { 72, -109, 70, 103, -104, 61, -26, -115, -73, 16, 122, 38, 90, -71, -79, 53, 107, 15, -43, 112, -82, -5, -83, 17, -12, 71, -36, -89, -20, -49, 80, -64 };
/*     */ 
/* 181 */   static final byte[][] encodeSubstitutionTable = { { 15, 8, 5, 7, 12, 2, 14, 9, 0, 1, 6, 13, 3, 4, 11, 10 }, { 2, 12, 14, 6, 15, 0, 1, 8, 13, 3, 10, 4, 9, 11, 5, 7 }, { 5, 2, 9, 15, 12, 4, 13, 0, 14, 10, 6, 8, 11, 1, 3, 7 }, { 15, 13, 2, 6, 7, 8, 5, 9, 0, 4, 12, 3, 1, 10, 11, 14 }, { 5, 14, 2, 11, 13, 10, 7, 0, 8, 6, 4, 1, 15, 12, 3, 9 }, { 8, 2, 15, 10, 5, 9, 6, 12, 0, 11, 1, 13, 7, 3, 4, 14 }, { 14, 8, 0, 9, 4, 11, 2, 7, 12, 3, 10, 5, 13, 1, 6, 15 }, { 1, 4, 8, 10, 13, 11, 7, 14, 5, 15, 3, 9, 0, 2, 6, 12 }, { 5, 3, 12, 8, 11, 2, 14, 10, 4, 1, 13, 0, 6, 7, 15, 9 }, { 6, 0, 11, 14, 13, 4, 12, 15, 7, 2, 8, 10, 1, 5, 3, 9 }, { 11, 5, 10, 14, 15, 1, 12, 0, 6, 4, 2, 9, 3, 13, 7, 8 }, { 7, 2, 10, 0, 14, 8, 15, 4, 12, 11, 9, 1, 5, 13, 3, 6 }, { 7, 4, 15, 9, 5, 1, 12, 11, 0, 3, 8, 14, 2, 10, 6, 13 }, { 9, 4, 8, 0, 10, 3, 1, 12, 5, 15, 7, 2, 11, 14, 6, 13 }, { 9, 5, 4, 7, 14, 8, 3, 1, 13, 11, 12, 2, 0, 15, 6, 10 }, { 9, 10, 11, 13, 5, 3, 15, 0, 1, 12, 8, 7, 6, 4, 14, 2 } };
/*     */ 
/* 201 */   static final byte[] encodePositionTable = { 3, 14, 15, 2, 13, 12, 4, 5, 9, 6, 0, 1, 11, 7, 10, 8 };
/*     */ 
/*     */   static byte[] encrypt(byte[] input)
/*     */   {
/*  41 */     byte encryptionSum = 0;
/*     */ 
/*  43 */     for (int loop = 0; loop < 2; ++loop)
/*     */     {
/*  45 */       for (int i = 0; i < 32; ++i)
/*     */       {
/*  52 */         byte encryptedByte = (byte)(input[i] + encryptionSum ^ input[(i + encryptionSum & 0x1F)] - positionTable[i]);
/*     */ 
/*  56 */         encryptionSum = (byte)(encryptionSum + encryptedByte);
/*     */ 
/*  58 */         input[i] = encryptedByte;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  64 */     byte[] output = new byte[16];
/*     */ 
/*  66 */     for (int i = 0; i < 32; ++i)
/*     */     {
/*  70 */       if ((i & 0x1) == 0) {
/*  71 */         output[(i / 2)] = Substitution[(input[i] & 0xFF)];
/*     */       }
/*     */       else
/*     */       {
/*     */         int tmp114_113 = (i / 2);
/*     */         byte[] tmp114_109 = output; tmp114_109[tmp114_113] = (byte)(tmp114_109[tmp114_113] | Substitution[(input[i] & 0xFF)] << 4);
/*     */       }
/*     */     }
/*  76 */     return output;
/*     */   }
/*     */ 
/*     */   static byte[] encryptPassword(byte[] id, int idOffset, byte[] password)
/*     */   {
/*  87 */     int length = password.length;
/*  88 */     int charPos = length - 1;
/*  89 */     int pwIdx = 0;
/*     */ 
/*  91 */     while ((length != 0) && (password[(charPos--)] == 0)) {
/*  92 */       --length;
/*     */     }
/*     */ 
/* 102 */     byte[] tempBuffer = new byte[32];
/*     */ 
/* 104 */     while (length >= 32)
/*     */     {
/* 106 */       for (int i = 0; i < 32; ++i)
/*     */       {
/*     */         int tmp59_57 = i;
/*     */         byte[] tmp59_55 = tempBuffer; tmp59_55[tmp59_57] = (byte)(tmp59_55[tmp59_57] ^ password[(pwIdx++)]);
/*     */       }
/* 110 */       length -= 32;
/*     */     }
/*     */ 
/* 113 */     if (length > 0)
/*     */     {
/* 115 */       charPos = pwIdx;
/*     */ 
/* 117 */       for (int i = 0; i < 32; ++i)
/*     */       {
/* 123 */         if (charPos == length)
/*     */         {
/* 125 */           charPos = pwIdx;
/*     */           int tmp115_113 = i;
/*     */           byte[] tmp115_111 = tempBuffer; tmp115_111[tmp115_113] = (byte)(tmp115_111[tmp115_113] ^ positionTable[i]);
/*     */         }
/*     */         else
/*     */         {
/*     */           int tmp133_131 = i;
/*     */           byte[] tmp133_129 = tempBuffer; tmp133_129[tmp133_131] = (byte)(tmp133_129[tmp133_131] ^ password[(charPos++)]);
/*     */         }
/*     */       }
/*     */     }
/* 133 */     for (int i = 0; i < 32; ++i)
/*     */     {
/*     */       int tmp165_163 = i;
/*     */       byte[] tmp165_161 = tempBuffer; tmp165_161[tmp165_163] = (byte)(tmp165_161[tmp165_163] ^ id[((i & 0x3) + idOffset)]);
/*     */     }
/*     */ 
/* 140 */     return encrypt(tempBuffer);
/*     */   }
/*     */ 
/*     */   static byte[] scramblePassword(String password, int objectID, byte[] challengeKey)
/*     */   {
/* 148 */     byte[] idBytes = new byte[4];
/*     */ 
/* 151 */     byte[] passBytes = password.toUpperCase().getBytes();
/*     */ 
/* 153 */     idBytes[0] = (byte)(objectID >>> 0 & 0xFF);
/* 154 */     idBytes[1] = (byte)(objectID >>> 8 & 0xFF);
/* 155 */     idBytes[2] = (byte)(objectID >>> 16 & 0xFF);
/* 156 */     idBytes[3] = (byte)(objectID >>> 24 & 0xFF);
/*     */ 
/* 159 */     byte[] cryptPass = encryptPassword(idBytes, 0, passBytes);
/*     */ 
/* 162 */     byte[] temp1 = encryptPassword(challengeKey, 0, cryptPass);
/*     */ 
/* 165 */     byte[] temp2 = encryptPassword(challengeKey, 4, cryptPass);
/*     */ 
/* 168 */     for (int i = 0; i < 16; ++i)
/*     */     {
/*     */       int tmp104_102 = i;
/*     */       byte[] tmp104_100 = temp1; tmp104_100[tmp104_102] = (byte)(tmp104_100[tmp104_102] ^ temp2[(15 - i)]);
/*     */     }
/* 171 */     byte[] output = new byte[8];
/*     */ 
/* 173 */     for (int i = 0; i < 8; ++i) {
/* 174 */       output[i] = (byte)(temp1[i] ^ temp1[(15 - i)]);
/*     */     }
/* 176 */     return output;
/*     */   }
/*     */ 
/*     */   public static byte[] encode(String password, byte[] key)
/*     */   {
/* 210 */     byte[] output = new byte[40];
/* 211 */     byte[] buffer = new byte[8];
/*     */ 
/* 216 */     byte[] passBytes = new byte[password.length()];
/* 217 */     passBytes = password.getBytes();
/*     */ 
/* 219 */     for (int i = 0; (i < passBytes.length) && (i < output.length); ++i)
/*     */     {
/* 221 */       output[i] = passBytes[i];
/*     */     }
/*     */ 
/* 225 */     for (int j = 0; j < 40; j += 8)
/*     */     {
/* 228 */       for (int loop = 0; loop < 16; ++loop)
/*     */       {
/* 231 */         for (int i = 0; i < 8; ++i)
/*     */         {
/* 233 */           buffer[i] = output[(i + j)];
/*     */         }
/*     */ 
/* 237 */         for (int i = 0; i < 8; ++i)
/*     */         {
/* 240 */           int temp = (buffer[i] ^ key[i]) & 0xFF;
/*     */ 
/* 245 */           buffer[i] = (byte)(encodeSubstitutionTable[(i * 2)][(temp & 0xF)] | encodeSubstitutionTable[(i * 2 + 1)][(temp >>> 4)] << 4);
/*     */         }
/*     */ 
/* 251 */         for (int i = 0; i < 16; ++i)
/*     */         {
/* 254 */           int temp = encodePositionTable[i] & 0xFF;
/* 255 */           if ((temp & 0x1) != 0)
/* 256 */             temp = buffer[(temp / 2)] >>> 4 & 0xF;
/*     */           else {
/* 258 */             temp = buffer[(temp / 2)] & 0xF;
/*     */           }
/*     */ 
/* 261 */           if ((i & 0x1) == 0) {
/* 262 */             output[(i / 2 + j)] = (byte)temp;
/*     */           }
/*     */           else
/*     */           {
/*     */             int tmp258_257 = (i / 2 + j);
/*     */             byte[] tmp258_250 = output; tmp258_250[tmp258_257] = (byte)(tmp258_250[tmp258_257] | (byte)(temp << 4));
/*     */           }
/*     */         }
/*     */ 
/* 268 */         int temp = key[7] & 0xFF;
/* 269 */         for (int i = 7; i > 0; --i)
/* 270 */           key[i] = (byte)(key[i] << 4 | key[(i - 1)] >>> 4 & 0xF);
/* 271 */         key[0] = (byte)(key[0] << 4 | temp >>> 4);
/*     */       }
/*     */     }
/* 274 */     return output;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.Crypt
 * JD-Core Version:    0.5.4
 */
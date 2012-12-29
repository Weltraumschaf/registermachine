# Register Machine

## Big picture

    Assembly Code
         |
         v
    +-----------+
    | Assembler |
    +-----------+
         |
         v
     Byte Code
         |
         v
    +-----------+
    |    VM     |
    +-----------+

## ASM Syntax

move    #1 #2       ; move content of reg 1 to reg 2
add     #1 #1       ; add reg 1 and reg 2 into reg 0
add     #1 3        ; add constant 3 and reg 1 into reg 0
jmp     3           ; jump to instruction 3
jmp     #3          ; jump to instruction position stored in reg 3

loadc   #3 1234     ; load constant integer into reg 3
loadc   #3 12.34    ; load constant float into reg 3
loadc   #3 nil      ; load constant nil into reg 3
loadc   #3 true     ; load constant true into reg 3
loadc   #3 false    ; load constant false into reg 3
loadc   #3 "string" ; load constant string into reg 3

## Byte Code format

    Pos     Hex                                 Description
    ---------------------------------------------------------------------------
    0000    ca  7e                              Header signature
    0002    02  00                              Version
    0004    0b  00 00 00                        Source name lengst 11
    0008    73  69 6d 70 6c  65 2e 6c 75 61 00  Source name string "simple.lua"
                                                ** function: [0] level 1
    0013    00                                  nups 0
    0014    00                                  numparams 0
    0015    02                                  is_vararg 2
    0016    02                                  maxstacksize 2
                                                * code:
    0017    09                                  sizecode 
    0018    02  01 00 00 00  00 00 00 00        [1] loadc 1 0  ; result
    0021    02  02 00 00 00  01 00 00 00        [2] loadc 2 1  ; incrementor
    002a    02  03 00 00 00  05 00 00 00        [3] loadc 3 5  ; end condition
    0033    02  04 00 00 00  3c 00 00 00        [4] loadc 4 3c ; jump position
    003c    11  01 00 00 00                     [5] println 1  ; jump here
    0041    03  01 00 00 00  02 00 00 00        [6] add 1 2    ; add register #1 and #2
    004a    01  00 00 00 00  10 00 00 00        [7] move 0 1   ; move value fron register #0 to #1
    0053    0d  01 00 00 00  03 00 00 00        [8] lt 1 3     ; test if value in register #1 < #3
    005c    0f  00 00 00 00  04 00 00 00        [9] test 0 4   ; if register #0 contians 0 jump to #4

## Opcodes

    mnemonic args   byte
    --------------------
    move     A B    0x01
    loadc    A B    0x02
    add      A B    0x03
    sub      A B    0x04
    mul      A B    0x05
    div      A B    0x06
    mod      A B    0x07
    pow      A B    0x08
    unm      A      0x09
    not      A      0x0a
    jmp      A      0x0b
    eq       A B    0x0c
    lt       A B    0x0d
    le       A B    0x0e
    test     A B    0x0f
    print    A      0x10
    println  A      0x11
    unkwonn         0xff

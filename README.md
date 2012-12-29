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

move    #1 #2   ; move content of reg 1 to reg 2
add     #1 #1   ; add reg 1 and reg 2 into reg 0
add     #1 3    ; add constant 3 and reg 1 into reg 0
jmp     3       ; jump to instruction 3
jmp     #3      ; jump to instruction position stored in reg 3
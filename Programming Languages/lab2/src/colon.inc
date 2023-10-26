%define pointer 0

%macro colon 2
        %%g_pointer:
        dq pointer
        db %1, 0
        %define pointer %%g_pointer

        %2:
%endmacro

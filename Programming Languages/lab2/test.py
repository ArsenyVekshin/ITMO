import subprocess
inp = ["", "first", "kurwaBober", "a"*255, "b"*256, "c"*300]
outs = ["", "first word explanation", "", "", ""]
errors = ["There is no such word in dictionary", "", "There is no such word in dictionary", "There is no such word in dictionary", "Max word length is 256 symb", "Max word length is 256 symb"]
test_results = ""
for i in range(len(inp)):
    p = subprocess.Popen(["./main"], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = p.communicate(input=inp[i].encode())
    stdout = stdout.decode()
    stderr = stderr.decode()
    if stdout == outs[i] and stderr == errors[i]:
        test_results += "A"
        print("Test +"+str(i+1)+" passed")
    else:
        test_results += "F"
        print(stdout, stderr, "Expected:",outs[i], errors[i])
print(test_results)
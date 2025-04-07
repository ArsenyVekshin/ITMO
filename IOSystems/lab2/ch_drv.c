#include <linux/module.h>
#include <linux/version.h>
#include <linux/kernel.h>
#include <linux/types.h>
#include <linux/kdev_t.h>
#include <linux/fs.h>
#include <linux/device.h>
#include <linux/cdev.h>
#include <linux/uaccess.h>

#define BUF_SIZE 1024
#define HISTORY_SIZE 10

static char device_buffer[BUF_SIZE];
static size_t data_len = 0;
static int counter;
static int history[HISTORY_SIZE];
static int history_index = 0;

static dev_t first;
static struct cdev c_dev; 
static struct class *cl;

static int my_open(struct inode *i, struct file *f) { 
    printk(KERN_INFO "Driver: open()\n");
    return 0;
}

static int my_close(struct inode *i, struct file *f) {
    printk(KERN_INFO "Driver: close()\n");
    return 0;
}

static ssize_t my_read(struct file *f, char __user *buf, size_t len, loff_t *off) {
    char str[256];
    ssize_t str_len;
    int i, idx;

    str_len = snprintf(str, sizeof(str), "Total symbols entered: %d\nHistory (last 10 inputs):\n", counter);
    
    for (i = 0; i < HISTORY_SIZE; i++) {
        idx = (history_index + i) % HISTORY_SIZE;
        if (history[idx] > 0) {
            str_len += snprintf(str + str_len, sizeof(str) - str_len, "%d\n", history[idx]);
        }
    }
    
    if (len < str_len) return -EINVAL;
    if (*off > 0) return 0;
    if (copy_to_user(buf, str, str_len)) return -EFAULT;
    
    *off += str_len;
    return str_len; 
}

static ssize_t my_write(struct file *f, const char __user *buf, size_t len, loff_t *off) {
    size_t bytes_to_write = (len > BUF_SIZE) ? BUF_SIZE : len;
    int i = 0;

    if (copy_from_user(device_buffer, buf, bytes_to_write))
        return -EFAULT; 

    device_buffer[bytes_to_write] = '\0';
    data_len = bytes_to_write;
    
    while (device_buffer[i] != '\0') {
        i++;
        counter++;
    }
    
    history[history_index] = i;
    history_index = (history_index + 1) % HISTORY_SIZE;

    printk(KERN_INFO "Driver: wrote %zu symbols\n", i);
    return bytes_to_write;
}

static struct file_operations mychdev_fops = {
    .owner = THIS_MODULE,
    .open = my_open,
    .release = my_close,
    .read = my_read,
    .write = my_write
};
 
static int __init ch_drv_init(void) {
    printk(KERN_INFO "Hello!\n");
    if (alloc_chrdev_region(&first, 0, 1, "ch_dev") < 0) {
        return -1;
    }

    if ((cl = class_create(THIS_MODULE, "chardrv")) == NULL) {
        unregister_chrdev_region(first, 1);
        return -1;
    }

    if (device_create(cl, NULL, first, NULL, "var1") == NULL) {
        class_destroy(cl);
        unregister_chrdev_region(first, 1);
        return -1;
    }

    cdev_init(&c_dev, &mychdev_fops);

    if (cdev_add(&c_dev, first, 1) == -1) {
        device_destroy(cl, first);
        class_destroy(cl);
        unregister_chrdev_region(first, 1);
        return -1;
    }

    counter = 0;
    history_index = 0;
    memset(history, 0, sizeof(history));
    
    return 0;
}
 
static void __exit ch_drv_exit(void) {
    cdev_del(&c_dev);
    device_destroy(cl, first);
    class_destroy(cl);
    unregister_chrdev_region(first, 1);
    printk(KERN_INFO "Bye!!!\n");
}
 
module_init(ch_drv_init);
module_exit(ch_drv_exit);
 
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Author");
MODULE_DESCRIPTION("The first kernel module with input history");

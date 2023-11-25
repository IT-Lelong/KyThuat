package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class KT01_Camera extends AppCompatActivity {
    Button btnTakePicture;
    TextView ttxtview;
    private KT01_DB db = null;
    String ID, b;
    Cursor cursor_3;
    Cursor cursor_5;
    String ID1;
    int STT, demso;
    String l_bp;
    String l_ngay,l_to;
    ImageView[] imageViews = new ImageView[6];
    LocalDate currentDate;
    String tenanh, luutenanh;
    TextView menuID;
    private static final int CAMERA_REQUEST = 1888;
    String l_checkdk = "";
    int aa;
    String[] myArray = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_camera);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageViews[0] = (ImageView) findViewById(R.id.imageView1);
        imageViews[1] = (ImageView) findViewById(R.id.imageView2);
        imageViews[2] = (ImageView) findViewById(R.id.imageView3);
        imageViews[3] = (ImageView) findViewById(R.id.imageView4);
        imageViews[4] = (ImageView) findViewById(R.id.imageView5);
        imageViews[5] = (ImageView) findViewById(R.id.imageView6);

        imageViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 0;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[0].getDrawable());

                textView1.setText(myArray[0]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        /*if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }*/
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();
                            loadanh(ID,l_ngay,ID1);
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh,loadhinh, l_ngay,ID1,"TC_FAA005","TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Ảnh đã được xóa", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    onResume();
                                }
                            });
                            dialog.dismiss();
                            //loadanh(ID,l_ngay,ID1);
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }

//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }

                loadanh(ID,l_ngay,ID1);
            }

        });

        imageViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 1;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                //Button btn3 = dialog.findViewById(R.id.btn3);
                //Button btn4 = dialog.findViewById(R.id.btn4);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[1].getDrawable());

                textView1.setText(myArray[1]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ;
//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }


            }

        });

        imageViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 2;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                //Button btn3 = dialog.findViewById(R.id.btn3);
                //Button btn4 = dialog.findViewById(R.id.btn4);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[2].getDrawable());

                textView1.setText(myArray[2]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                        //dialog.dismiss();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                        //dialog.dismiss();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ;
//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }


            }

        });

        imageViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 3;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                //Button btn3 = dialog.findViewById(R.id.btn3);
                //Button btn4 = dialog.findViewById(R.id.btn4);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[3].getDrawable());

                textView1.setText(myArray[3]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ;
//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }


            }

        });

        imageViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 4;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                //Button btn3 = dialog.findViewById(R.id.btn3);
                //Button btn4 = dialog.findViewById(R.id.btn4);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[4].getDrawable());

                textView1.setText(myArray[4]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ;
//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }


            }

        });

        imageViews[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 5;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT01_Camera.this);
                dialog.setContentView(R.layout.kt01_dialog_enlarged_image);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                //Button btn3 = dialog.findViewById(R.id.btn3);
                //Button btn4 = dialog.findViewById(R.id.btn4);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView.setImageDrawable(imageViews[5].getDrawable());

                textView1.setText(myArray[5]);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    //dialog.dismiss();
                                    dialog.dismiss();
                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT01_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray[aa];
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
                                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                    String a = newDirectory + "/" + tenanh ;
                                    File fileToDelete = new File(a);
                                    boolean deleted = fileToDelete.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                        db.delete_tenanh(tenanh);
                                        Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
                                        cursor.moveToFirst();
                                        int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
                                        int loadhinh = num23 - 1;
                                        if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        }
                                        db.appendUPDAEhinhanh(ID, luutenanh, loadhinh, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
                                        Toast.makeText(KT01_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                    }
                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    //dialog.dismiss();
                                    dialog.dismiss();
                                    //onResume();
                                }

                            });
                            dialog.dismiss();

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }


            }

        });


        btnTakePicture = findViewById(R.id.btn_take_picture12);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenCamera.class);
                intent.putExtra("ngay", l_ngay);
                intent.putExtra("bophan", ID1);
                intent.putExtra("hangmuc", ID);
                intent.putExtra("to", l_to);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Thêm cờ vào Intent
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveImage(photo);
        }

    }

    private void saveImage(Bitmap finalBitmap) {
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                finalBitmap,
                tenanh,
                "Image Context"
        );
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".png";
        File file = new File(savedImageURL, fname);
        if (file.exists()) file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void luudulieuanh() {
        db = new KT01_DB(this);
        db.open();
        Cursor cursor = db.getstt(ID, ID1, l_ngay);
        cursor.moveToFirst();
        int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
        demso=num+1;
        db.append1(ID, l_ngay, ID1, String.valueOf(demso), tenanh);
        db.appendUPDAEhinhanh(ID, luutenanh, STT, l_ngay, ID1, "TC_FAA005", "TC_FAA011");
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        loadanh(ID, l_ngay, ID1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle getbundle = getIntent().getExtras();

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);
        System.out.println(formattedTime);

        l_ngay = getbundle.getString("l_ngay");
        l_to = getbundle.getString("l_to");
        if (l_ngay != null) {

            ID1 = getbundle.getString("l_bp");
            l_ngay = getbundle.getString("l_ngay");
        } else {
            try {
                InputStream is = getApplicationContext().openFileInput("mydata.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                ID1 = sb.toString();
                currentDate = LocalDate.now();
                l_ngay = String.valueOf(currentDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = new KT01_DB(this);
        db.open();

        if (Constant_Class.UserFactory.equals("DH")) {
            if (l_to.equals("Tổ A")){
                l_to="ToA";
            }
            if (l_to.equals("Tổ B")){
                l_to="ToB";
            }
            if (l_to.equals("Tổ C")){
                l_to="ToC";
            }
            if (l_to.equals("Tổ D")){
                l_to="ToD";
            }

            ID = getbundle.getString("ID");
            Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
            //Cursor cursor = db.getstt(ID, ID1, l_ngay);
            cursor.moveToFirst();
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
            //int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
            STT = num + 1;
            //tenanh = ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            //tenanh = l_to + "_" +ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            tenanh = ID + "_" +l_to + "_" + l_ngay + "_" + ID1 + "_" + STT;
            luutenanh = ID + "_" +l_to + "_" + l_ngay + "_" + ID1;
            ttxtview = findViewById(R.id.menuID);
            ttxtview.setText(tenanh);
            if (num >= 1) {
                loadanh(ID, l_ngay, ID1);
            }
        }else {
            l_to="XBL";
            ID = getbundle.getString("ID");
            Cursor cursor = db.demsttanh(ID, ID1, l_ngay);
            //Cursor cursor = db.getstt(ID, ID1, l_ngay);
            cursor.moveToFirst();
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
            //int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
            STT = num + 1;
            //tenanh = ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            //tenanh = l_to + "_" +ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            tenanh = ID + "_" +l_to + "_" + l_ngay + "_" + ID1 + "_" + STT;
            luutenanh = ID + "_" +l_to + "_" + l_ngay + "_" + ID1;
            ttxtview = findViewById(R.id.menuID);
            ttxtview.setText(tenanh);
            if (num >= 1) {
                loadanh(ID, l_ngay, ID1);
            }
        }
    }

    private void loadanh(String key, String l_ngay, String l_bp) {
        cursor_3 = db.lananh(key, l_ngay, l_bp);
        cursor_3.moveToFirst();
        //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
        int num = cursor_3.getCount();
        if (num > 6) {
            num = 6;
        }
        cursor_5 = db.lananh2(key, l_ngay, l_bp);
        cursor_5.moveToFirst();
        //int tong = ((num2 - 6) + 1);
        int showanh = 0;
        if (num > 0) {
            for (int i = 1; i <= num; i++) {
                try {
                    @SuppressLint("Range") String tenanh = cursor_5.getString(cursor_5.getColumnIndex("tenanh"));
                    // String   a = "/storage/emulated/0/Pictures/KT010103_2023-02-25_13_45_37_ABI3100000.jpg";
                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                    String a = newDirectory + "/" + tenanh ;
                    //num2 = num2 - 1;
                    File imgFile = new File(a);

                    if (imgFile.exists()) {
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                            imageViews[showanh].setImageBitmap(myBitmap);
                            myArray[showanh] = tenanh;

                            showanh = showanh + 1;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    String err = e.toString();
                }
                cursor_5.moveToNext();
            }
        }

    }


}

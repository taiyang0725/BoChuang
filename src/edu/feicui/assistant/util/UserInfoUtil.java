package edu.feicui.assistant.util;

import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import edu.feicui.assistant.R;
import edu.feicui.assistant.mode.Person;

/**
 * ����һ����ȡ��ϵ����Ϣ�Ĺ�����
 * */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class UserInfoUtil {

	/**
	 * �����Ķ���
	 * */
	private Context context;

	public ArrayList<Person> getPersonInfo1(Context context) {
		ArrayList<Person> list = new ArrayList<Person>();
		String name = "";
		String num = "";
		for (int i = 0; i < 20; i++) {
			Person person = new Person();
			name = "A" + i;
			num = "139929900" + i;
			person.setName(name);
			person.setTelNum(num);
			list.add(person);

		}

		return list;
	}

	/**
	 * ��ȡ��ϵ����Ϣ
	 * */
	public ArrayList<Person> getPersonInfo(Context context) {
		int a = 0;
		ArrayList<Person> list = new ArrayList<Person>();
		ContentResolver myResolver = context.getContentResolver();
		/** contacts�� */
		Cursor contactsCursor = myResolver.query(
				ContactsContract.Contacts.CONTENT_URI, null,

				null, null, ContactsContract.Contacts.DISPLAY_NAME
						+ " COLLATE LOCALIZED ASC");

		/* �ж��Ƿ�����ϵ����Ϣ */
		if (contactsCursor.getCount() > 0) {
			/*
			 * �α��ʼָ���ѯ����ĵ�һ����¼���Ϸ���ִ��moveToNext�������ж�
			 * ��һ����¼�Ƿ���ڣ�������ڣ�ָ����һ����¼�����򣬷���false��
			 */
			while (contactsCursor.moveToNext()) {

				Person person = new Person();
				String idContacts = contactsCursor.getString(contactsCursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				person.setIdContacts(idContacts);
				Cursor rawContactsIdCur = myResolver.query(
						RawContacts.CONTENT_URI, null, RawContacts.CONTACT_ID
								+ " = ?", new String[] { idContacts }, null);

				if (rawContactsIdCur.moveToFirst()) {
					// ��ȡ��һ����¼��RawContacts._ID�е�ֵ
					String rawContactsId = rawContactsIdCur
							.getString(rawContactsIdCur
									.getColumnIndex(RawContacts._ID));
					person.setRawContactsId(rawContactsId);
				}
				rawContactsIdCur.close();

				/** �Ƿ��е绰���� */
				if (contactsCursor
						.getInt(contactsCursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
					/****************************************** ���ڵ绰�������Ϣ ***********************************/
					Cursor dataCursor = myResolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ? ", new String[] { idContacts },
							null);

					while (dataCursor.moveToNext()) {

						/** ��ȡ�绰���� */
						String telNum = dataCursor
								.getString(dataCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						/** ��ȡ���� */
						String name = dataCursor
								.getString(dataCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						person.setName(name);
						/** �绰�������� */
						int tape = dataCursor
								.getInt(dataCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

						if (tape == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) {
							person.setHomeTel(telNum);

						} else if (tape == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
							person.setTelNum(telNum);

						} else if (tape == ContactsContract.CommonDataKinds.Phone.TYPE_WORK) {
							person.setOfficeTel(telNum);
						}

					}
					dataCursor.close();
					/****************************************** �����û�ͼ�����Ϣ ***********************************/
					// Cursor photoCursor = myResolver
					// .query(Uri
					// .parse(ContactsContract.Contacts.PHOTO_URI),
					// null,
					// ContactsContract.CommonDataKinds.Photo._ID
					// + " =? ",
					// new String[] { idContacts }, null);
					// while (photoCursor.moveToNext()) {
					// int photoid = photoCursor
					// .getInt(photoCursor
					// .getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
					// person.setUserIcon(photoid);
					// }
					// photoCursor.close();
					/****************************************** ���ڹ�������Ϣ ***********************************/
					Cursor organizationCursor = myResolver.query(
							ContactsContract.Data.CONTENT_URI, new String[] {
									ContactsContract.Data._ID,
									Organization.COMPANY, Organization.TITLE },
							ContactsContract.Data.CONTACT_ID + "=?" + " AND "
									+ ContactsContract.Data.MIMETYPE + "='"
									+ Organization.CONTENT_ITEM_TYPE + "'",
							new String[] { idContacts }, null);
					while (organizationCursor.moveToNext()) {
						/** ְ��ְ�� */
						String workName = organizationCursor
								.getString(organizationCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
						person.setWorkName(workName);
						/** ��λ���� */
						String unitName = organizationCursor
								.getString(organizationCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
						person.setUnitName(unitName);

					}
					organizationCursor.close();
					/****************************************** ����E-mail����Ϣ ***********************************/
					Cursor mailCursor = myResolver.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { idContacts }, null);
					while (mailCursor.moveToNext()) {
						String eMail = mailCursor
								.getString(mailCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						person.seteMail(eMail);
					}
					mailCursor.close();
					/****************************************** ���������������Ϣ ***********************************/
					try {
						Cursor zipCursor = myResolver
								.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
										new String[] { idContacts }, null);
						while (zipCursor.moveToNext()) {
							String postalcode = zipCursor
									.getString(zipCursor
											.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
							person.setPostalcode(postalcode);
							/** ��ַ */
							String address = zipCursor
									.getString(zipCursor
											.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
							person.setAddress(address);
						}
						zipCursor.close();
					} catch (Exception e) {
						person.setPostalcode(context.getResources().getString(
								R.string.cannot_detection));

					}
					/****************************************** ���ڱ�ע����Ϣ ***********************************/
					Cursor noteCursor = myResolver
							.query(ContactsContract.Data.CONTENT_URI,
									new String[] {
											ContactsContract.Data._ID,
											ContactsContract.CommonDataKinds.Note.NOTE },
									ContactsContract.Data.CONTACT_ID + "=?"
											+ " AND "
											+ ContactsContract.Data.MIMETYPE
											+ "='" + Note.CONTENT_ITEM_TYPE
											+ "'", new String[] { idContacts },
									null);
					while (noteCursor.moveToNext()) {
						String note = noteCursor
								.getString(noteCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
						person.setRemark(note);
					}
					noteCursor.close();
				}

				list.add(person);
			}
			contactsCursor.close();
		}

		return list;
	}

	/** ��ȡͼƬ�ķ��� */
	public Bitmap getBitmap(Context context, String id) {

		Uri contactUri = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));

		InputStream inputStream = ContactsContract.Contacts
				.openContactPhotoInputStream(context.getContentResolver(),
						contactUri);

		return BitmapFactory.decodeStream(inputStream);

	}

	/**
	 * �û�ͼ������
	 * */
	private int[] selpic;

	public int[] userIcon() {

		selpic = new int[] { R.drawable.image1, R.drawable.image2,
				R.drawable.image3, R.drawable.image4, R.drawable.image5,
				R.drawable.image6, R.drawable.image7, R.drawable.image8,
				R.drawable.image9, R.drawable.image10, R.drawable.image11,
				R.drawable.image12, R.drawable.image13, R.drawable.image14,
				R.drawable.image15, R.drawable.image16, R.drawable.image17,
				R.drawable.image18, R.drawable.image19, R.drawable.image20,
				R.drawable.image21, R.drawable.image22, R.drawable.image23,
				R.drawable.image24, R.drawable.image25, R.drawable.image26,
				R.drawable.image27, R.drawable.image28, R.drawable.image29,
				R.drawable.image30, };

		return selpic;

	}

	/**
	 * ������ϵ����Ϣ�ķ���
	 * */
	public void insert(Person person, Context context) {
		Log.d("asasasssssssssss", person.toString());
		ArrayList<ContentProviderOperation> list = new ArrayList<ContentProviderOperation>();

		int rawContactInsertIndex = 0;

		list.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());

		/** ���� */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.StructuredName.DISPLAY_NAME,
						person.getName()).build());
		Log.d("������aaaaaaaaaaaaaaaaaaaaaa", person.getName());
		/** �ֻ��� */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Phone.NUMBER, person.getTelNum())
				.withValue(CommonDataKinds.Phone.TYPE, Phone.TYPE_MOBILE)
				.build());

		/** �칫�ҵ绰 */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Phone.NUMBER, person.getOfficeTel())
				.withValue(CommonDataKinds.Phone.TYPE, Phone.TYPE_WORK).build());
		/** ��ͥ�绰 */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Phone.NUMBER, person.getHomeTel())
				.withValue(CommonDataKinds.Phone.TYPE, Phone.TYPE_HOME).build());
		Log.d("��ͥ����", person.getHomeTel());
		/** ְ��ְ�� */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Organization.TITLE,
						person.getWorkName()).build());
		/** ��λ���� */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Organization.COMPANY,
						person.getUnitName()).build());
		/** �������� */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.StructuredPostal.POSTCODE,
						person.getPostalcode()).build());
		/** E-mail */
		list.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Email.ADDRESS, person.geteMail())
				.withValue(Email.TYPE, Email.TYPE_WORK).build());
		/** ��ַ */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
						person.getAddress()).build());
		/** ��ע */
		list.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						CommonDataKinds.Note.CONTENT_ITEM_TYPE)
				.withValue(CommonDataKinds.Note.NOTE, person.getRemark())
				.build());

		try {
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY,
					list);

		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (OperationApplicationException e) {

			e.printStackTrace();
		}

	}

	/** ��ѯ�ķ��� */
	public ArrayList<Person> query(ArrayList<Person> list, String info) {

		ArrayList<Person> query = new ArrayList<Person>();

		for (Person person : list) {

			if ((person.getName() != null && person.getName().startsWith(info))
					|| (person.getTelNum() != null && person.getTelNum()
							.startsWith(info))) {

				query.add(person);
			}

		}
		return query;

	}

	/**
	 * ɾ��ָ����ϵ��
	 * */
	public void delete(Context context, String rawContactsId) {
		ContentResolver contentResolver = null;
		ArrayList<ContentProviderOperation> list = new ArrayList<ContentProviderOperation>();

		contentResolver = context.getContentResolver();

		list.add(ContentProviderOperation.newDelete(
				ContentUris.withAppendedId(
						ContactsContract.RawContacts.CONTENT_URI,
						Long.parseLong(rawContactsId))).build());

		try {
			contentResolver.applyBatch(ContactsContract.AUTHORITY, list);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (OperationApplicationException e) {

			e.printStackTrace();
		}

	}

	/** �޸���Ϣ */
	public void update(Person person, Context context, Person oldPer) {

		Log.d("������", person.toString());
		ArrayList<ContentProviderOperation> list = new ArrayList<ContentProviderOperation>();

		ContentResolver contentResolver = context.getContentResolver();

		/** �޸����� */
		if (oldPer.getName() != person.getName()) {
			list.add(ContentProviderOperation

					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(
							Data.RAW_CONTACT_ID + "=? AND "

							+ ContactsContract.Data.MIMETYPE + "=?",
							new String[] {
									String.valueOf(oldPer.getRawContactsId()),
									StructuredName.CONTENT_ITEM_TYPE })
					.withValue(StructuredName.DISPLAY_NAME, person.getName())
					.build());
		}

		Log.d("������", person.getName());

		/** �޸��ֻ��� */
		if (oldPer.getTelNum() != person.getTelNum()) {
			list.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(
							Data.RAW_CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE
									+ "=? AND " + Phone.TYPE + " = ?",
							new String[] {
									String.valueOf(oldPer.getRawContactsId()),
									Phone.CONTENT_ITEM_TYPE,
									Phone.TYPE_MOBILE + "" })
					.withValue(Phone.NUMBER, person.getTelNum()).build());
		}

		Log.d("�º�", person.getTelNum());
		/** �޸ļ�ͥ���� */
		if (oldPer.getHomeTel() != person.getHomeTel()) {
			list.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(
							Data.RAW_CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE
									+ "=? AND " + Phone.TYPE + " = ?",
							new String[] {
									String.valueOf(oldPer.getRawContactsId()),
									Phone.CONTENT_ITEM_TYPE,
									Phone.TYPE_HOME + "" })
					.withValue(Phone.NUMBER, person.getHomeTel()).build());
		}
		Log.d("��ͥ����", person.getHomeTel());
		/** �޸İ칫�ҵ绰 */
		if (oldPer.getOfficeTel() != person.getOfficeTel()) {
			list.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(
							Data.RAW_CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE
									+ "=? AND " + Phone.TYPE + " = ?",
							new String[] {
									String.valueOf(oldPer.getRawContactsId()),
									Phone.CONTENT_ITEM_TYPE,
									Phone.TYPE_WORK + "" })
					.withValue(Phone.NUMBER, person.getOfficeTel()).build());
		}
		Log.d("�����绰", person.getTelNum());
		/** ְ��ְ�� */
		if (oldPer.getWorkName() != person.getWorkName()) {
			list.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(
							Data.RAW_CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + "=?",
							new String[] {
									String.valueOf(oldPer.getRawContactsId()),
									Organization.CONTENT_ITEM_TYPE })
					.withValue(Organization.TITLE, person.getWorkName())
					.build());
		}
		Log.d("ְ��ְ��", person.getWorkName());
		/** ��λ���� */
		list.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] {
								String.valueOf(oldPer.getRawContactsId()),
								Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.COMPANY, person.getUnitName()).build());
		Log.d("��λ����", person.getUnitName());
		/** ��ַ */
		list.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] {
								String.valueOf(oldPer.getRawContactsId()),
								StructuredPostal.CONTENT_ITEM_TYPE })
				.withValue(StructuredPostal.FORMATTED_ADDRESS,
						person.getAddress()).build());
		Log.d("��ַ", person.getAddress());
		/** E-mail */
		list.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] {
								String.valueOf(oldPer.getRawContactsId()),
								Email.CONTENT_ITEM_TYPE })
				.withValue(Email.ADDRESS, person.geteMail()).build());
		Log.d("E-mail", person.geteMail());
		/** ��ע */
		list.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] {
								String.valueOf(oldPer.getRawContactsId()),
								Note.CONTENT_ITEM_TYPE })
				.withValue(Note.NOTE, person.getRemark()).build());
		Log.d(" ��ע", person.getRemark());

		try {
			contentResolver.applyBatch(ContactsContract.AUTHORITY, list);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

	}

}
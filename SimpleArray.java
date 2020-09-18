class SimpleArray
{
    public static int [] squareAll(int values[])
    {
        int [] newValues = new int[values.length]; // This allocates an array of integers.

        for(int count = 0; count < values.length; count++)
        {
            newValues[count] = values[count] * values[count];
        }
        return newValues;
    }
}
